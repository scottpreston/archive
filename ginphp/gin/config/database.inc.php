<?php
$site_name = "ginphp.codegin.com";

$db_type = "mysql";
$db_server = "localhost";

if (SERVER_NAME == "dev." . $site_name) {
    $db_login = "root";
    $db_password = "password";
    $db_name = "ginphp";
} else if (SERVER_NAME == "test." . $site_name) {
    $db_login = "root";
    $db_password = "password";
    $db_name = "test";
} else {
    $db_login = "production-login";
    $db_password = "prodction-password";
    $db_name = "production-db";
}