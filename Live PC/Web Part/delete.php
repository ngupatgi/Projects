<?php 
require 'cresidentials1235.php';
unlink("fragments/".$_GET["file"]);
$conne->query("delete from flag where file='".$_GET["file"]."'");

echo $conne->error;

//echo "deleted ".$_GET["file"];

?>