<?php 
require 'cresidentials1235.php';

$res=$conne->query("select file from flag where connection_id='".$_GET["myid"]."' and direction='".$_GET["direction"]."' and file!='tmp_name' order by num asc limit 20");
$a=0;
if($res!=null)
	while($me=mysqli_fetch_array($res))
	{
		if($a==0){
			echo $me[0];
			$a++;
		}
		else{
			echo ";".$me[0];
		}
	}
else
	echo $conne->error;


?>