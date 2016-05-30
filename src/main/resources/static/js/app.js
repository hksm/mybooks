var app = angular.module('mymovies', ['ngRoute', 'ngSanitize', 'oi.select', 'ui.bootstrap', 
                                      'smart-table', 'mgcrea.ngStrap.navbar']);

app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl: 'partials/home.html',
			controller: 'homeController'
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