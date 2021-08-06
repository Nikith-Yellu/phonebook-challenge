<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html lang="en" >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>PhoneBook</title>
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/styles.css">
</head>
<body ng-app="phonebookApp" ng-controller="phonebookCtrl">
<script src="js/angular/angular.js"></script>
<script src="js/angular/angular-route.js"></script>
<script src="js/angular/angular-messages.js"></script>
<script src="js/angular/angular-cookies.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="js/phonebook.js"></script>
<script type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<div class="container">
		<div class="pure-g">
			<div class="pure-u-1">
				<div class="header">
					<img class="logo" src="img/phonebook.png"/>
					<p>v 1.0</p>
				</div>
				
			</div>
		</div>
		<div class="pure-g">
		    <div class="pure-u-sm-1 pure-u-1-3">
		    	<div class="box">
		    		<h2><i class="fa fa-user-plus"></i>New contact</h2>
		    		<form class="pure-form" ng-submit="addContact()">
					    <fieldset class="pure-group">
					        <input type="text" class="pure-input-1-2" ng-model="fname" placeholder="First Name">
					        <input type="text" class="pure-input-1-2" ng-model="lname" placeholder="Last Name">
					        <input type="tel" class="pure-input-1-2" ng-model="phone" placeholder="Phone" maxlength="10">
					    </fieldset>
					    <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">
					    <i class="fa fa-user-plus"></i>Add</button>
					</form>
				</div>
				<div ng-if="addContactResponse==true"> {{addContactResponseMessage}}</div>
			</div>
		    <div class="pure-u-sm-1 pure-u-1-3">
				<div class="box">
		    		<h2><i class="fa fa-search"></i>Search contact</h2>
		    		<form class="pure-form" ng-submit="search()">
		    			<fieldset class="pure-group">
					    	<input type="text" class="pure-input-1-2" ng-model="searchKeyword">
					     </fieldset>
					    <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">
					    <i class="fa fa-search"></i>Search</button>
					</form>
					<table ng-if ="displayResult == true" class="pure-table">
					    <thead>
					        <tr>
					            <th>First Name</th>
					            <th>Last Name</th>
					            <th>Phone</th>
					        </tr>
					    </thead>
					
					    <tbody>
					       <tr >
					            <td>{{contact.fname}}</td>
					            <td>{{contact.lname}}</td>
					            <td>{{contact.phone}}</td>
					        </tr>
					
					        
					    </tbody>
					</table>
				</div>
			</div>
			<div class="pure-u-sm-1 pure-u-1-3" ng-init="getContacts()">
				<div class="box">
		    		<h2><i class="fa fa-users"></i> Contacts</h2>
	    			<table class="pure-table">
					    <thead>
					        <tr>
					            <th>First Name</th>
					            <th>Last Name</th>
					            <th>Phone</th>
					        </tr>
					    </thead>
					
					    <tbody>
					       <tr ng-repeat="contact in contacts track by $index">
					            <td>{{contact.fname}}</td>
					            <td>{{contact.lname}}</td>
					            <td>{{contact.phone}}</td>
					        </tr>
					
					        
					    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>