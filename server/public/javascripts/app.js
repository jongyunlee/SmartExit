var app = angular.module('eexit', ['ui.router']);

app.config([
    '$stateProvider',
    '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {
	$stateProvider
	.state('home', {
	    url: '/home',
	    templateUrl: '/home.html',
	    controller: 'MainCtrl',
	    resolve: {
		piPromise: ['pis', function(pis) {
		    return pis.getAll();
		}]
	    }
	}).state('pis', {
	    url: '/pis/{id}',
	    templateUrl: '/pis.html',
	    controller: 'PisCtrl',
	    resolve: {
		pi: ['$stateParams', 'pis', function($stateParams, pis) {
		    var pi = pis.get($stateParams.id);
		    return pi;
		}]
	    }
	});
	$urlRouterProvider.otherwise('home');
    }
]);

app.factory('pis', [
    '$http',
    function($http) {
	var o = {
	    pis: []
	};
	o.getAll = function() {
	    return $http.get('/pis').success(function(data) {
		angular.copy(data, o.pis);
	    });
	};
	o.create = function(pi) {
	    return $http.post('/pis', pi).success(function(data) {
		o.pis.push(data);
	    });
	};
	o.get = function(id) {
	    return $http.get('/pis/' + id).then(function(res) {
		return res.data;
	    });
	};

	o.remove = function(id) {
	    return $http.delete('/pis/' + id).success(function(data) {
		for (var index = 0; index < o.pis.length; index++) {
		    if (o.pis[index]._id === id) {
			o.pis.splice(index, 1);
			index--;
		    }
		}
	    });
	};

	o.changeMessage = function(id, message) {
	    return $http.put('/pis/' + id + '/message', {'message': message});
	};
	o.changeDestination = function(id, destination) {
	    return $http.put('/pis/' + id + '/destination', {'destination' : destination});
	};
	o.changeState = function(id, state) {
	    return $http.put('/pis/' + id + '/state', {'state': state});
	};
	o.changeDirection = function(id, direction) {
	    return $http.put('/pis/' + id + '/direction', {'direction': direction});
	};
	return o;
    }
]);

app.controller('MainCtrl', [
    '$scope',
    'pis',
    function($scope, pis) {
	$scope.pis = pis.pis;
	$scope.addPi = function() {
	    if(!$scope.direction || $scope.direction === '') { return; }
	    if(!$scope.location || $scope.location === '') { return; }
	    pis.create({direction: $scope.direction, location: $scope.location});
	    $scope.direction = 'left';
	    $scope.location = '';
	};
	$scope.deletePi = function(id) {
	    pis.remove(id);
	};
    }
]);

app.controller('PisCtrl', [
    '$scope',
    'pis',
    'pi',
    function($scope, pis, pi) {
	console.log('hello');
	$scope.pi = pi;
	$scope.changeMessage = function() {
	    if(!$scope.message || $scope.message === '') { return; }
	    pis.changeMessage(pi._id, $scope.message).then(function(res) {
		$scope.pi =  res.data;
	    });
	    $scope.message = '';
	};
	$scope.changeDestination = function(id, destination) {
	    if (!$scope.destination || $scope.destination === '') { return; }
	    pis.changeDestination(pi._id, $scope.destination).then(function(res) {
		$scope.pi = res.data;
	    });
	    $scope.destination = '';
	};
	$scope.changeState = function(id, state) {
	    if (!$scope.state || $scope.state === '') { return; }
	    pis.changeState(pi._id, $scope.state).then(function(res) {
		$scope.pi = res.data;
	    });
	    $scope.state = '';
	};
	$scope.changeDirection = function(id, direction) {
	    if (!$scope.direction || $scope.direction === '') { return; }
	    pis.changeDirection(pi._id, $scope.direction).then(function(res) {
		$scope.pi = res.data;
	    });
	    $scope.direction = '';
	};
    }
]);
