(function (){
	
	var app = angular.module('phonebookApp',['ngRoute','ngMessages','ngCookies']);
	app.factory('pageCache',function (){
		return {
			errors: new Array()
		};
	});
	
	app.controller('phonebookCtrl',['$scope','$rootScope', function ($scope,$rootScope){

		$scope.displayResult = false;
		$scope.addContactResponse = false;
		//Fetches all contacts to display on initial page load
		$scope.getContacts = function(){
			var url = "/PhoneBookTest/phonebook";
	    	$.ajax({
	          url: url,
	          method: 'GET',
	        }).done(function(result) {
	          $scope.$apply(function(){
	          $scope.contacts = JSON.parse(result);
	        });
	          console.log($scope.contacts);
	        }).fail(function(err) {
	          throw err;
	        });
		}
		
		//Search for the user provided keyword
		$scope.search = function(){
			var url="/PhoneBookTest/phonebook?search="+$scope.searchKeyword;
			$.ajax({
          	url: url,
          	method: 'GET',
        	}).done(function(result) {
          	$scope.$apply(function(){
            $scope.contact = JSON.parse(result);
			$scope.displayResult = true;
        	});
        	}).fail(function(err) {
          	throw err;
        	});
		}
		
		//Store the contact data in DB
		$scope.addContact = function(){
			var request = {
				fname: $scope.fname,
				lname: $scope.lname,
				phone: $scope.phone
			}
			request = JSON.stringify(request);
			$scope.fname="";
			$scope.lname="";
			$scope.phone="";
			var url="/PhoneBookTest/phonebook";
			$.ajax({
          	url: url,
          	method: 'POST',
			data: request,
			contentType:"application/json",
			dataType:"json",
        	}).done(function(result) {
          	$scope.$apply(function(){
			$scope.addContactResponseMessage  = result;
			$scope.addContactResponse = true;
			$scope.getContacts();
        	});
        	}).fail(function(err) {
			$scope.addContactResponseMessage= err.responseTxt;
			$scope.addContactResponse = true;
          	$scope.getContacts();
        });
		
		}
		
		
	}])
})();