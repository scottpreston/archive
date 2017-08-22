<?
function build($file = "php://stdout") {
    ob_flush();
    ob_start();
    template();
    $out = ob_get_contents();
    ob_flush();
    $fp = fopen($file, 'w');
    $out = str_replace("<%", "<?", $out);
    $out = str_replace("%>", "?>", $out);
    $bytes = fwrite($fp, $out);
}

function template() {
?>
<% echo ('test 123'); %>
<?
}

build();
