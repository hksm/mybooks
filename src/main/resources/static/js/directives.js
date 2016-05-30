app.directive('book', function() {
	return {
		restrict: 'E',
		scope: {
			data: '='
		},
		templateUrl: 'templates/book-widget.html'
	};
});

app.directive('confirmPopover', function() {
	return {
		restrict: 'A',
		transclude: true,
		replace: true,
		scope: {
			confirmObj: '=',
			confirmFunc: '&'
		},
		templateUrl: function(elem, attrs) {
			return 'templates/confirm-' + elem[0].tagName.toLowerCase() + '.html';
		}
	}
});