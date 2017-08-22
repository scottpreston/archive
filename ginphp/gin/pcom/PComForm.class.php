<?php
class PComForm extends BasePcom{

	var $action;
	var $method;
	var $elements = array();

	/*
	 * @param $action - where form POST
	 * @param $name - the name of the form
	 * @param $method - default POST, other option GET
	 */
    function PComForm($action="",$name="",$method="POST") {
		$this->method = $method;
        $this->name = $name;
        $this->action = $action;
        $this->id = $this->name;
    }

    function create() {
    	$out = '<form action="'.$this->action.'" name="'.$this->name.'" id="'.$this->id.'" method="'.$this->method.'" style="'.$this->style.'">';
    	return $out;
    }

    function close() {
    	echo "</form>";
    }

    function add($elt) {
    	array_push($this->elements, $elt);
    }

    function write_all() {
    	$out = $this->create()."\n";
    	foreach ($this->elements as $elt) {
    		$out .= $elt->create()."\n";
    	}
    	$out .= '</form>'."\n";
    	echo $out;
    }

    function write_validation() {
    	$out = '<script type="text/javascript">$(document).ready(function(){ $("#'.$this->id.'").validate({'."\n";
    	$out .= 'rules: {'."\n";
    	foreach ($this->elements as $elt) {
			if ($elt instanceof PComPassword
			|| $elt instanceof PComTextbox
			|| $elt instanceof PComTextarea
			|| $elt instanceof PComRadio
			|| $elt instanceof PComCheckbox
			|| $elt instanceof PComDatebox
			&& $elt->required == true
			) {
    			$out .= $elt->get_val_rule() .',';
			}
    	}
    	$out = substr($out, 0,-1); // trim last comma
    	$out .= '},'."\n".' messages: {'."\n";
    	foreach ($this->elements as $elt) {
			if ($elt instanceof PComPassword
			|| $elt instanceof PComTextbox
			|| $elt instanceof PComTextarea
			|| $elt instanceof PComRadio
			|| $elt instanceof PComCheckbox
			|| $elt instanceof PComDatebox
			&& $elt->required == true
			) {
    			$out .= $elt->get_val_msg() .',';
    		}
    	}
    	$out = substr($out, 0,-1); // trim last comma
    	$out .= '},'; // msg end
    	$out .=' success: function(label) { label.html("&nbsp;").addClass("checked")
				}';
    	$out .= '});});</script>'."\n";
    	echo $out;
    }


}
?>