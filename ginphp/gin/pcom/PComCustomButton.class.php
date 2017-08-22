<?php
class PComCustomButton extends BasePcom{

	var $label;
	var $onclick;
	var $type;
	var $href;
	var $icon;

    function PComCustomButton($label="Button",$type="add",$href="",$onclick="") {
    	$this->label = $label;
    	$this->onclick = $onclick;
    	$this->href = $href;
    	$this->icon="/images/$type.png";
    }

    function create() {
    	$out = '<div class="custom_button"><a href="'.$this->href.'" class="custom_button"
    	onclick="'.$this->onclick.'"><img src="'.$this->icon.'" height="32" width="32" 
    	class="custom_button" border="0">'.$this->label.'</a><br><br></div>';
    	return $out;
    }
}
?>