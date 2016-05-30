app.factory('dataFactory', function($http) {
	var dataFactory = {};
	
	dataFactory.getAuthors = function() {
		return $http.get('/api/authors');
	};
	
	dataFactory.getAuthor = function(id) {
		return $http.get('/api/authors/' + id);
	};
	
	dataFactory.insertAuthor = function(author) {
		return $http.post('/api/authors', author);
	};

	dataFactory.updateAuthor = function(author) {
		return $http.put('/api/authors/' + author.id, author);
	};
	
	dataFactory.deleteAuthor = function(id) {
		return $http.delete('/api/authors/' + id);
	};

	dataFactory.getCountries = function() {
		return $http.get('/api/countries');
	};
	
	dataFactory.getLanguages = function() {
		return $http.get('/api/languages');
	};

	dataFactory.getGenres = function() {
		return $http.get('/api/genres');
	};
	
	dataFactory.getGenre = function(id) {
		return $http.get('/api/genres/' + id);
	};
	
	dataFactory.insertGenre = function(genre) {
		return $http.post('/api/genres', genre);
	};

	dataFactory.updateGenre = function(genre) {
		return $http.put('/api/genres/' + genre.id, genre);
	};
	
	dataFactory.deleteGenre = function(id) {
		return $http.delete('/api/genres/' + id);
	};

	dataFactory.getBooks = function() {
		return $http.get('/api/books');
	};
	
	dataFactory.getBook = function(id) {
		return $http.get('/api/books/' + id);
	};
	
	dataFactory.insertBook = function(book) {
		return $http.post('/api/books', book);
	};

	dataFactory.updateBook = function(book) {
		return $http.put('/api/books/' + book.id, book);
	};
	
	dataFactory.deleteBook = function(id) {
		return $http.delete('/api/books/' + id);
	};
	
	return dataFactory;
});

app.factory('wikiSummary', function($http) {
	var wikiSummary = {};
	
	wikiSummary.getSummary = function(name) {
		var baseUrl = 'https://en.wikipedia.org/w/api.php?action=query' +
			'&prop=extracts&callback=JSON_CALLBACK&format=json&exintro=&redirects=1&titles=';
		return $http.jsonp(baseUrl + name);
	};
	
	return wikiSummary;
});