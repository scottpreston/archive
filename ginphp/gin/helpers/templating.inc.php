<?php
function include_header($title="",$keywords="",$description="",$subnav="") {
	include_once(get_theme_root() .  '/header.php');
}

function include_footer() {
	include_once(get_theme_root() .  '/footer.php');
}

function include_nav() {
	include_once(get_theme_root() .  '/nav.php');
}

function include_admin_header($title="") {
	include_once(get_admin_theme_root() .  '/header.php');
}

function include_admin_footer() {
	include_once(get_admin_theme_root() .  '/footer.php');
}

function include_admin_nav() {
	include_once(get_admin_theme_root() .  '/nav.php');
}

function get_theme() {
	return get_option(CURRENT_THEME);
}

function get_admin_theme() {
	return get_option(ADMIN_THEME);
}

function get_option($name) {
	global $options;
	return $options[$name];
}

function get_theme_root(){
	return WEB_ROOT .  '/themes/' . get_theme();
}

function get_admin_theme_root(){
	return WEB_ROOT .  '/themes/' . get_admin_theme();
}

function get_site_name() {
	$site_name = get_option(SITE_NAME);
	if ($site_name == "") {
		$site_name = "Site Name";
	}
}
?>