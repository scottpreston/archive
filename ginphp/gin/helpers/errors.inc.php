<?
// defining these in request scope
$other_errors = array();
$form_errors = array();
$required_fields = array();

function add_required($name) {
   	global $required_fields;
    array_push($required_fields,$name);
}

function add_form_error($err) {
	global $form_errors;
	array_push($form_errors,$err);
}

function add_other_error($err) {
	global $other_errors;
	array_push($other_errors,$err);
}

function get_error_count() {
	global $form_errors,$other_errors;	
	return count($form_errors) + count($other_errors);
}

function clear_error_count() {
	global $form_errors,$other_errors;	
	$form_errors = array();
	$other_errors = array();
}

// use this top print them out
function get_errors() {
	// TODO : code - need to create error logger in the database as well as create google analytics message
	global $other_errors,$form_errors;
	// display errors on same page
	if (count($other_errors) > 0) {
		$html = "<div id='otherErrors'>System Error:";
		$html .= "<ul>";
		$hit = false;
		foreach ($other_errors as $value) {
			$hit = true;
			$html .= "<li>".$value."</li>";
		}
		$html .= "</ul></div>";
		$other_errors = array();
		if ($hit) {
			echo $html;
		}
		$html = "";
	}
	if (count($form_errors) > 0) {
		$html = "<div id='formErrors'>Form Error:";
		$html .= "<ul>";
		$hit = false;
		foreach ($form_errors as $value) {
			$hit = true;
			$html .= "<li>".$value."</li>";
		}
		$html .= "</ul></div>";
		$form_errors = array();
		if ($hit) {
			echo $html;
		}
		$html = "";
	}
}