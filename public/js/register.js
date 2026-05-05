function register(){

const username = document.getElementById("username").value;
const password = document.getElementById("password").value;

console.log("USERNAME:", username);
console.log("PASSWORD:", password);

if(!username || !password){
alert("Please enter all fields");
return;
}

fetch("/api/register",{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({
username: username,
password: password
})
})
.then(res=>res.json())
.then(data=>{
alert(data.message);
});

}