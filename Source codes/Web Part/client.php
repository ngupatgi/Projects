<?php 

require 'cresidentials1235.php';


$res=$conne->query("select client from manager where connection_id='".$_GET["connection_id"]."'");
if($_GET["request"]=="host")
    $res=$conne->query("select username from manager where connection_id='".$_GET["connection_id"]."'");

$me=mysqli_fetch_array($res);
echo $me[0];



?>
