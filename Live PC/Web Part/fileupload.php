<?php 
require 'cresidentials1235.php';

$conne->query("insert into flag(connection_id,direction,file) values('".$_POST["connection_id"]."','".$_GET["direction"]."','tmp_name')");
$num=mysqli_insert_id($conne);
//echo $_POST["connection_id"];


if(move_uploaded_file($_FILES["userdata"]["tmp_name"],"fragments/newfile45t".$num))
{
	echo "upload success size:".filesize("fragments/newfile45t".$num);
	$conne->query("update flag set file='newfile45t$num' where num='$num'");
}
else{
	$conne->query("delete from flag where file='tmp_name'");
	echo "\n My error : ".$_FILES["userdata"]["error"];
}


?>