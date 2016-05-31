var app = angular.module('mymovies', ['ngRoute', 'ngSanitize', 'ngCookies', 'oi.select', 
                                      'ui.bootstrap', 'smart-table', 'mgcrea.ngStrap.navbar']);

app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl: 'partials/home.html',
			controller: 'homeController'
		})
		.when('/login', {
			templateUrl: 'partials/login.html',
			controller: 'loginController'
		})
		.when('/register', {
			templateUrl: 'partials/register.html',
			controller: 'registerController'
		})
		.when('/books', {
			templateUrl: 'partials/books.html',
			controller: 'booksController'
		})
		.when('/authors', {
			templateUrl: 'partials/authors.html',
			controller: 'authorsController'
		})
		.when('/genres', {
			templateUrl: 'partials/genres.html',
			controller: 'genresController'
		})
		.when('/books/:bookId', {
			templateUrl: 'partials/book-details.html',
			controller: 'bookDetailsController'
		})
		.otherwise('/');
});