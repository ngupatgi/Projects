<?php

require 'cresidentials1235.php';

generate_connectionid($conne); 
function generate_connectionid($conne)
{

$myid=uniqid();
$res=mysqli_fetch_array($conne->query("select count(connection_id) from manager where connection_id='".$myid."'"));
if($res[0]==0)
{
        $conne->query("insert into manager(connection_id,username) values('$myid','".$_POST["username"]."')");
        echo $myid.";Success";
}
else
   generate_connectionid($conne);     
}


?>