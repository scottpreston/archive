<?php
class PComRegularButton extends BasePcom {

    var $label;
    var $onclick;

    function PComRegularButton($label = "Button", $onclick = "", $className = "") {
        $this->label = $label;
        $this->name = $this->name_from_label($label);
        $this->onclick = $onclick;
        $this->className = $className;
    }

    function create() {
        $out = '<button type="button"';
        $out .= ' name="submitbutton"';
        $out .= ' onclick="'.$this->onclick. '"';
        $out .= ' style="' . $this->style . '"';
        $out .= ' id="' . $this->id . '"';
        $out .= ' class="' . $this->className . '">';
        $out .= $this->label . '</button>';
        return $out;
    }
}

?>