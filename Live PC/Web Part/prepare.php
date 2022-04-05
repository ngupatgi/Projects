<?php 
require 'cresidentials1235.php';

$conid=$_GET["myid"];

$res=$conne->query("select file from flag where connection_id='$conid'");
	if($res!=null)
		while($row=mysqli_fetch_array($res))
		{
			
			$conne->query("delete from flag where file='".$row[0]."'");
			unlink("fragments/".$row[0]);
		}

$conne->query("update manager set power=0,other=null where connection_id='".$conid."'");

echo "Success";


?>