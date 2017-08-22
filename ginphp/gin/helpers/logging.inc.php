<?
//defined in the config.inc.php file
define('LOG_FILE','');
define('LOG_MODE','');
define('LOG_MODE_DEBUG','debug');
define('LOG_MODE_WARNING','warning');
define('LOG_MODE_ERROR','error');

function gin_log($message) {
    error_log($message, 3, get_option(LOG_FILE));
}