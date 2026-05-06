function login(){

const username = document.getElementById("username").value;
const password = document.getElementById("password").value;

alert("Username: " + username + " Password: " + password);

fetch("http://localhost:5000/api/login", {
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({username,password})
})
.then(res=>res.json())
.then(data=>{

if(data.success){
alert("Login success");
window.location.href="index.html";
}else{
alert("Invalid credentials");
}

})
.catch(err=>{
alert("Error connecting to server");
console.log(err);
});

}