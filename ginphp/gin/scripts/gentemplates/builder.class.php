<?php
abstract class builder {

    function builder() {
    }

    public function build($file = "php://stdout") {
        ob_flush();
        ob_start();
        $this->_build();
        $out = ob_get_contents();
        ob_flush();
        $fp = fopen($file, 'w');
        $out = str_replace("<%", "<?", $out);
        $out = str_replace("%>", "?>", $out);
        $bytes = fwrite($fp, $out);
        fclose($fp);
    }

    public function get_classname_from_table($table) {
        $cls = explode("_", $table);
        $cname = "";
        if (count($cls) > 1) {
            for ($i = 0; $i < count($cls); $i++) {
                $tmp = $cls[$i];
                $cname .= ucfirst($tmp);
            }
        } else {
            $cname = $cls[0];
        }
        return ucfirst($cname);
    }
}

?>