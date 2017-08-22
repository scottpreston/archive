<?php
if (version_compare(phpversion(), '5.1.0', '<') == true) { die ('PHP5.1 Or Greater Only...'); }
session_start();
error_reporting(E_ERROR); // set for production
header("Cache-control: private");
include_once(WEB_ROOT."/gin/helpers/logging.inc.php");
include_once(WEB_ROOT."/gin/config/config.inc.php");
include_once(WEB_ROOT."/gin/helpers/utils.inc.php");
include_once(WEB_ROOT."/gin/helpers/errors.inc.php");
include_once(WEB_ROOT."/gin/helpers/templating.inc.php");
include_once(WEB_ROOT."/gin/lib/adodb5/adodb.inc.php");
//include_once(WEB_ROOT."/lib/adodb5/adodb-active-record.inc.php");

// application specific
include_once(WEB_ROOT."/app/config/config.inc.php"); // app specific stuff

// assumes ADODB5
$conn=&ADONewConnection($db_type);
$conn->PConnect($db_server,$db_login,$db_password,$db_name);
// does not use active record yet
//ADOdb_Active_Record::SetDatabaseAdapter($conn);

// this is the autoload class that will look at all the places in the lib folder for classes
function __autoload($class) {
	$loaded = false;
	$ado_class = str_replace("_", "-", strtolower($class));
	$lib_path = array (
		WEB_ROOT . "/gin/base/" . $class . ".class.php",
		WEB_ROOT . "/gin/pcom/" . $class . ".class.php",
		WEB_ROOT . "/gin/lib/adodb5/" . $ado_class . ".inc.php",
		WEB_ROOT . "/app/controllers/" . $class . ".class.php",
		WEB_ROOT . "/app/models/" . $class . ".class.php",
        WEB_ROOT . "/app/validators/" . $class . ".class.php",
		WEB_ROOT . "/app/classes/" . $class . ".class.php"
	);

	foreach ($lib_path as $lib) {
		if (file_exists($lib)) {
			require_once ($lib);
			$loaded = true;
			break;
		}
	}

	if ($loaded = false) {
		gin_log("Can't find a file for class: $class  \nPagename: $pageName");
	}
}