app.controller('navigationController', function($rootScope, $scope, $location, 
										$http, dataFactory, loginService, authCookie) {
	$scope.navbarCollapsed = false;
	
	// Authentication & Authorization
	var checkCookie = function() {
		$rootScope.user = authCookie.getCookie() || {
			username: '',
			token: null,
			roleUser: false,
			roleAdmin: false
		};
		$http.defaults.headers.common.Authorization = 
			$rootScope.user.token ? 'Bearer ' + $rootScope.user.token : '';
	};
	
	checkCookie();
		
	$scope.logout = function() {
		$rootScope.user = {
			username: '',
			token: null,
			roleUser: false,
			roleAdmin: false
		};
		$http.defaults.headers.common.Authorization = '';
		authCookie.removeCookie();
		$location.path('/');
	};
	
	// Search bar functions
	$scope.getBookByTitle = function(val) {
		return dataFactory.getBooks()
			.then(function(response) {
				return response.data.title;
		});
	};
	
	// Lazy load search list
	$scope.bookSearchList = [];
	$scope.getBooksSearch = function() {
		if ($scope.bookSearchList.length === 0) {
			dataFactory.getBooks()
				.then(function(response) {
					$scope.bookSearchList = response.data;
				});
		}
	};
	
	// On select
	$scope.onSelect = function($item, $model) {
		$location.path('/books/' + $model.id);
	};
});

app.controller('loginController', function($rootScope, $scope, $location, loginService, 
										   authCookie, checkPermission, $http, $q) {
	
	checkPermission.notLogged();
	
	// ui-alert
	$scope.alerts = [];
	var addAlert = function(type, message) {
		$scope.alerts.push({
			type: type,
			message: message
		});
	};
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	
	$scope.rememberMe = true;
	
	var checkRoles = function() {
		var deferred = $q.defer();
		
		loginService.hasRole('user')
			.then(function(roleUser) {
				$rootScope.user.roleUser = roleUser;
				loginService.hasRole('admin')
					.then(function(roleAdmin) {
						$q(function(resolve, reject) {
							$rootScope.user.roleAdmin = roleAdmin;
							resolve();
						}).then(function() {
							deferred.resolve();
						});
					});
			});
		
		return deferred.promise;
		
	};
	
	$scope.login = function(credentials) {
		loginService.login(credentials)
			.then(function(token) {
				$rootScope.user.username = credentials.username;
				$rootScope.user.token = token;
				$http.defaults.headers.common.Authorization = 'Bearer ' + token;
				checkRoles().then(function() {
					if ($scope.rememberMe) {
						authCookie.saveCookie($rootScope.user);
					} else {
						authCookie.removeCookie();
					}
					$location.path('/books');
				});
			}, function(response) {
				addAlert('danger', 'Invalid login!');
			});
	};
});

app.controller('homeController', function($scope, dataFactory, wikiSummary, $sce) {
	
	$scope.randomBooks = [];
	
	var getBooks = function() {
		dataFactory.getBooks()
			.then(function(response) {
				var bookList = response.data;
				if (bookList.length <= 3) {
					bookList.forEach(function(elem) {
						$scope.randomBooks.push(elem);
					});
				} else {
					var cont = 0;
					for (var i = 0; i < bookList.lenth; i++) {
						var book = bookList[Math.floor(Math.random()*bookList.length)];
						if ($scope.randomBooks.length === 0 ||
							$scope.randomBooks[0].title !== book.title &&
							$scope.randomBooks[1].title !== book.title) {
								$scope.randomBooks.push(book);
						}
						if ($scope.randomBooks.length === 3) {
							break;
						}
					}
				}
				getSummary($scope.randomBooks);
			});
	};
	
	var getSummary = function(bookList) {
		bookList.forEach(function(book, i) {
			wikiSummary.getSummary(book.title)
				.then(function(response) {
					var key = Object.keys(response.data.query.pages);
					var string = response.data.query.pages[key[0]].extract;
					$scope.randomBooks[i].summary = $sce.trustAsHtml(string.substring(0, string.indexOf('</p>')+4));
				});
		});
	};
	
	getBooks();
});

app.controller('booksController', function($scope, $q, dataFactory, checkPermission) {
	
	$scope.book = {};
	$scope.isProcessing = false;
	$scope.searchAuthor = '';
	$scope.searchTitle = '';
	
	checkPermission.hasRoleUser();
	
	// ui-alert
	$scope.alerts = [];
	
	var addAlert = function(type, message) {
		$scope.alerts.push({
			type: type,
			message: message
		});
	};
	
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	
	// ui-datepicker
	$scope.altInputFormats = ['d!/M!/yyyy'];
	$scope.publishingDate = {
		opened:  false
	};
	$scope.publishingDateOptions = {
		showWeeks: false,
		maxDate: new Date(),
		minDate: new Date(1500, 1, 1),
		startingDay: 1
	};
	$scope.openPublishingDate = function() {
		$scope.publishingDate.opened = true;
	};

	// Book CRUD
	var clearForm = function() {
		$scope.book = {};
	};

	var getBooks = function() {
		dataFactory.getBooks()
			.then(function(response) {
				$scope.bookList = response.data;
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
		
	var getBook = function(id) {
		dataFactory.getBook(id)
			.then(function(response) {
				$scope.book = response.data;
				$scope.book.publishingDate = new Date($scope.book.publishingDate);
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
	
	$scope.saveBook = function(book) {
		$scope.isProcessing = true;
		if (book.id) {
			dataFactory.updateBook(book)
				.then(function(response) {
					getBooks();
					clearForm();
					addAlert('success', 'Book updated with success!');
					$scope.isProcessing = false;
				}, function(response) {
					addAlert('danger', response.statusText);
					$scope.isProcessing = false;
				});		
		} else {
			dataFactory.insertBook(book)
				.then(function(response) {
					getBooks();
					clearForm();
					addAlert('success', 'Book inserted with success!');
					$scope.isProcessing = false;
				}, function(response) {
					addAlert('danger', response.statusText);
					$scope.isProcessing = false;
				});
		}
	};
	
	$scope.editBook = function(id) {
		getBook(id);
	}
	
	$scope.deleteBook = function(id) {
		dataFactory.deleteBook(id)
			.then(function(response) {
				addAlert('success', 'Book deleted with success!');
				getBooks();
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	}
	
	// Lazy load language select
	$scope.languageList = null;
	$scope.getLanguages = function() {
		var deferred = $q.defer();
		
		if ($scope.languageList !== null) {
			deferred.resolve($scope.languageList);
		} else {
			dataFactory.getLanguages()
				.then(function(response) {
					$scope.languageList = response.data;
					deferred.resolve(response.data);
				}, function(response) {
					deferred.reject(response);
				});
		}
		return deferred.promise;	
	};	
	
	// Get Authors
	var getAuthors = function() {
		dataFactory.getAuthors()
			.then(function(response) {
				$scope.authorList = response.data;
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
	
	// Get Genres
	var getGenres = function() {
		dataFactory.getGenres()
			.then(function(response) {
				$scope.genreList = response.data;
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
	
	$scope.getTooltip = function(arr) {
		var string = arr.slice(1).reduce(function(str, curr) {
			return str + ', ' + curr.name;
		}, arr[0].name);
		return string;
	};
	
	$scope.clearFormButton = clearForm;
	
	getBooks();
	getAuthors();
	getGenres();
});

app.controller('authorsController', function($scope, $q, dataFactory, checkPermission, $rootScope) {
	
	$scope.author = {};
	$scope.isProcessing = false;
	$scope.searchName = '';
	
	checkPermission.hasRoleUser();
	
	// ui-datepicker
	$scope.altInputFormats = ['d!/M!/yyyy'];
	$scope.birthDate = {
		opened:  false
	};
	$scope.birthDateOptions = {
		showWeeks: false,
		maxDate: new Date(),
		minDate: new Date(1500, 1, 1),
		startingDay: 1
	};
	$scope.openBirthDate = function() {
		$scope.birthDate.opened = true;
	};
	
	// ui-alert
	$scope.alerts = [];
	var addAlert = function(type, message) {
		$scope.alerts.push({
			type: type,
			message: message
		});
	};
	
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	// Author CRUD
	var clearForm = function() {
		$scope.author = {};
	};

	var getAuthors = function() {
		dataFactory.getAuthors()
			.then(function(response) {
				$scope.authorList = response.data;
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
		
	var getAuthor = function(id) {
		dataFactory.getAuthor(id)
			.then(function(response) {
				$scope.author = response.data;
				$scope.author.birthDate = new Date($scope.author.birthDate);
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
	
	$scope.saveAuthor = function(author) {
		$scope.isProcessing = true;
		if (author.id) {
			dataFactory.updateAuthor(author)
				.then(function(response) {
					getAuthors();
					clearForm();
					addAlert('success', 'Author updated with success!');
					$scope.isProcessing = false;
				}, function(response) {
					addAlert('danger', response.statusText);
					$scope.isProcessing = false;
				});		
		} else {
			dataFactory.insertAuthor(author)
				.then(function(response) {
					getAuthors();
					clearForm();
					addAlert('success', 'Author inserted with success!');
					$scope.isProcessing = false;
				}, function(response) {
					addAlert('danger', response.statusText);
					$scope.isProcessing = false;
				});
		}
	};
	
	$scope.editAuthor = function(id) {
		getAuthor(id);
	}
	
	$scope.deleteAuthor = function(id) {
		dataFactory.deleteAuthor(id)
			.then(function(response) {
				addAlert('success', 'Author deleted with success!');
				getAuthors();
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	}

	// Lazy load country select
	$scope.countryList = null;
	$scope.getCountries = function() {
		var deferred = $q.defer();
		
		if ($scope.countryList !== null) {
			deferred.resolve($scope.countryList);
		} else {
			dataFactory.getCountries()
				.then(function(response) {
					$scope.countryList = response.data;
					deferred.resolve(response.data);
				}, function(response) {
					deferred.reject(response);
				});
		}
		return deferred.promise;	
	}

	$scope.clearFormButton = clearForm;
	
	getAuthors();
});

app.controller('genresController', function($scope, dataFactory, checkPermission) {

	checkPermission.hasRoleUser();
	
	$scope.genre = {};
	$scope.isProcessing = false;
	$scope.selected = -1;
	
	// ui-alert
	$scope.alerts = [];
	var addAlert = function(type, message) {
		$scope.alerts.push({
			type: type,
			message: message
		});
	};
	
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	// Genre CRUD
	var clearForm = function() {
		$scope.genre = {};
	};

	var getGenres = function() {
		dataFactory.getGenres()
			.then(function(response) {
				$scope.genreList = response.data;
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
		
	var getGenre = function(id) {
		dataFactory.getGenre(id)
			.then(function(response) {
				$scope.genre = response.data;
			});
	};
	
	$scope.saveGenre = function(genre) {
		$scope.isProcessing = true;
		if (genre.id) {
			dataFactory.updateGenre(genre)
				.then(function(response) {
					getGenres();
					clearForm();
					addAlert('success', 'Genre updated with success!');
					$scope.isProcessing = false;
					$scope.selected = genre;
				}, function(response) {
					addAlert('danger', response.statusText);
					$scope.isProcessing = false;
				});		
		} else {
			dataFactory.insertGenre(genre)
				.then(function(response) {
					getGenres();
					clearForm();
					addAlert('success', 'Genre inserted with success!');
					$scope.isProcessing = false;
					$scope.selected = -1;
				}, function(response) {
					addAlert('danger', response.statusText);
					$scope.isProcessing = false;
				});
		}
	};
	
	$scope.editGenre = function(id) {
		getGenre(id);
	};
	
	$scope.deleteGenre = function(id) {
		dataFactory.deleteGenre(id)
			.then(function(response) {
				addAlert('success', 'Genre deleted with success!');
				getGenres();
				$scope.selected = -1;
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
	
	$scope.clearFormButton = clearForm;
	
	// Get Books
	var getBooks = function() {
		dataFactory.getBooks()
			.then(function(response) {
				$scope.bookList = response.data;
			}, function(response) {
				addAlert('danger', response.statusText);
			});
	};
	
	getGenres();
	getBooks();
});

app.controller('bookDetailsController', function($scope, dataFactory, $routeParams, wikiSummary, $sce) {
	
	$scope.book = {};
	$scope.summary = '';
	
	// ui-alert
	$scope.alerts = [];
	
	var addAlert = function(type, message) {
		$scope.alerts.push({
			type: type,
			message: message
		});
	};
	
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	
	// Get Book
	var getBook = function(id) {
		dataFactory.getBook(id)
			.then(function(response) {
				$scope.book = response.data;
				wikiSummary.getSummary($scope.book.title)
					.then(function(response) {
						var key = Object.keys(response.data.query.pages);
						$scope.summary = $sce.trustAsHtml(response.data.query.pages[key[0]].extract);
					}, function(response) {
						addAlert('danger', response.statusText);
					});
			}, function(response) {
				addAlert('danger', response.statusText);
			})
	};
	
	getBook($routeParams.bookId);
});

app.controller('registerController', function($scope, $http) {
	
	// ui-alert
	$scope.alerts = [];
	
	var addAlert = function(type, message) {
		$scope.alerts.push({
			type: type,
			message: message
		});
	};
	
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
	
	$scope.register = function(user) {
		if (user.password !== $scope.matchingPassword) {
			addAlert('danger', 'Password fields must match!');
		} else {
			$http.post('/api/auth/register', user)
				.then(function(response) {
					addAlert('success', 'Registration completed with success!');
				}, function(response) {
					addAlert('danger', response.statusText);
				});
		}
	};
});