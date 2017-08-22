<?php
define ('WEB_ROOT', dirname(__FILE__));
define ('PAGE_NAME', $_SERVER['SCRIPT_NAME']);
define ('SERVER_NAME', $_SERVER['SERVER_NAME']);
define ('REQUEST_URI', $_SERVER['REQUEST_URI']);
define('CURRENT_THEME', 'current_theme');
define('ADMIN_THEME', 'admin_theme');
define('SITE_NAME', 'site_name');
define ("EXT", ".class.php");
include_once(WEB_ROOT . "/gin/gin.inc.php");