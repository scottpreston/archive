<?php
// database
include("database.inc.php");

// other TBD...
$options = array (
    CURRENT_THEME =>'default', // change this to a new theme in themes directory
    ADMIN_THEME => 'admin',    // change this to a new theme in themes directory
    SITE_NAME => SERVER_NAME,   // by default this is the server name, you can change to a directory here.
    LOG_MODE => LOG_MODE_ERROR,
    LOG_FILE => WEB_ROOT.'/app/log/error.log'
);