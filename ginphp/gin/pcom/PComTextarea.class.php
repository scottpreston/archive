<?php
class PComTextarea extends BasePcom {

    var $label;
    var $value;
    var $name;
    var $rows;
    var $cols;
    var $required;
    var $onblur;

    function PComTextarea($label = "", $value = "") {
        $this->label = $label;
        $this->name = $this->name_from_label($label);
        $this->value = $value;
    }

    function create() {
        $out = $this->start_tag();
        $out .= '<textarea cols="' . $this->cols . '"';
        $out .= ' rows="' . $this->rows . '"';
        $out .= ' name="' . $this->name . '"';
        $out .= ' style="' . $this->style . '"';
        $out .= ' class="' . $this->className . '"';
        $out .= ' id="' . $this->name . '">';
        $out .= $this->value . '</textarea>';
        $out .= $this->end_tag();
        return $out;
    }
}

?>