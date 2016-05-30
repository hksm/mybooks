app.filter('filterByGenre', function() {
	return function(arr, value) {
		if (value === -1) {
			return arr;
		}
		var filtered = [];
		angular.forEach(arr, function(elem) {
			angular.forEach(elem.genres, function(el) {
				if (el.id === value.id) {
					filtered.push(elem);
				}
			});
		});
		return filtered;
	};
});

app.filter('filterByAuthor', function() {
	return function(arr, value) {
		var filtered = [];
		angular.forEach(arr, function(book) {
			for (var index = 0, len = book.authors.length; index < len; index++) {
				var name = book.authors[index].firstName.toUpperCase() + ' ' + book.authors[index].lastName.toUpperCase();
				if (name.replace(/\./g,'').indexOf(value.toUpperCase().replace(/\./g,'')) !== -1) {
					filtered.push(book);
					break;
				}
			}
		});
		return filtered;
	};
});

app.filter('filterByTitle', function() {
	return function(arr, value) {
		var filtered = [];
		angular.forEach(arr, function(book) {
			if (book.title.toUpperCase().indexOf(value.toUpperCase()) !== -1) {
				filtered.push(book);
			}
		});
		return filtered;
	};
});

app.filter('filterByFirstOrLastName', function() {
	return function(arr, value) {
		var filtered = [];
		angular.forEach(arr, function(author) {
			var name = author.firstName.toUpperCase() + ' ' + author.lastName.toUpperCase();
			if (name.replace(/\./g,'').indexOf(value.toUpperCase().replace(/\./g,'')) !== -1) {
				filtered.push(author);
			}
		});
		return filtered;
	};
});