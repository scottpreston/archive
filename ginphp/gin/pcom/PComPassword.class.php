<?php
class PComPassword extends BasePcom{

	var $value;
	var $size = 16;
	var $max = 16;
	
	function PComPassword($label="") {
		$this->label = $label;
		$this->name = $this->name_from_label($label);
		$this->id = $this->name;
	}

	function create() {
		$out = $this->start_tag();
    	$out .= '<input type="password"';
    	$out .= ' size="'.$this->size.'"';
		$out .= ' name="'.$this->name.'"';
		$out .= ' value="'.$this->value.'"';
		$out .= ' id="'.$this->id.'"';
		$out .= ' class="'.$this->className.'"';
		$out .= ' maxlength="'.$this->max.'"';
    	$out .= '/>' .$this->end_tag();
    	return $out;
	}
}
?>