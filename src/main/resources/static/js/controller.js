
var cartApp = angular.module('cartApp', []);


//var eMusicStoreRestCart = 'http://localhost:8102/musicshop/rest/cart/';
var eMusicStoreRestCart = '/musicshop2/rest/cart/';

cartApp.controller('cartController', function ($scope, $http) {
    $scope.cart = null;
    $scope.cartId = "10101";




    $scope.initCartById = function (cartId) {
        $scope.cartId = cartId;
        $scope.refreshCart(cartId);
    };

    $scope.refreshCart = function () {
        $http.get(eMusicStoreRestCart + $scope.cartId)

                .then(function (response) {
                    $scope.cart = response.data;
                },
                        function () {
                            alert("refresh ERROR");
                        }
                );
    };

    $scope.removeFromCart = function (itemId) {
        $http.delete(eMusicStoreRestCart + 'delete/' + itemId).then(
                function () {
                    $scope.refreshCart();
                },
                function () {
                    alert("removeFromCart ERROR");
                }
        );
    };

    $scope.clearCart = function () {
        $http.delete(eMusicStoreRestCart + $scope.cartId + "/clear").then(
                        function () {
                            $scope.refreshCart();
                        },
                        function () {
                            alert("clearCart ERROR");
                        }
                );
    };



//
//    $scope.addToCart = function (productId) {
//        $http.put(eMusicStoreRestCart + 'add/' + productId, {})
////                .finally(
////                        function () {
////                            $scope.refreshCart($http.get(eMusicStoreRestCart + $scope.cartId));
////                            alert("addToCart  OK");
////                        });
//                .then(
//                        function () {
//                            $scope.refreshCart($http.get(eMusicStoreRestCart + $scope.cartId));
//                            alert("1 product with ID " + productId + " added to cart.");
//                        },
//                        function () {
//                            alert("addToCart ERROR");
//                        }
//                );
//    };





});