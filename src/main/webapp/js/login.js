document.getElementById("btn_registro").addEventListener("click", registrar);
document.getElementById("btn_ingresar").addEventListener("click", ingresar);
window.addEventListener("resize", anchoPagina);

var contenedor_login_register = document.querySelector(".contenedor_login_register");
var form_login  = document.querySelector(".form_login");
var form_register  = document.querySelector(".form_register");
var caja_trasera_login = document.querySelector(".caja_trasera_login");;
var caja_trasera_register = document.querySelector(".caja_trasera_register");;

function anchoPagina(){
    if(window.innerWidth >850){
        caja_trasera_register.style.display = "block";
        caja_trasera_login.style.display = "block";
    }

    else{
        caja_trasera_register.style.display = "block";
        caja_trasera_register.style.opacity = "1";
        caja_trasera_login.style.display = "none";
        form_login.style.display = "block";
        form_register.style.display = "none";
        contenedor_login_register.style.left = "0px";
    }
}

anchoPagina();

function ingresar(){
    if(window.innerWidth >850){
        form_register.style.display = "none";
        contenedor_login_register.style.left = "10px";
        form_login.style.display = "block";
        caja_trasera_register.style.opacity = "1";
        caja_trasera_login.style.opacity = "0";
    }

    else{
        form_register.style.display = "none";
        contenedor_login_register.style.left = "0px";
        form_login.style.display = "block";
        caja_trasera_register.style.display = "block";
        caja_trasera_login.style.display = "none";
    }
    
}

function registrar(){
    if(window.innerWidth >850){
        form_register.style.display = "block";
        contenedor_login_register.style.left = "410px";
        form_login.style.display = "none";
        caja_trasera_register.style.opacity = "0";
        caja_trasera_login.style.opacity = "1";
    }

    else{
        form_register.style.display = "block";
        contenedor_login_register.style.left = "0px";
        form_login.style.display = "none";
        caja_trasera_register.style.display = "none";
        caja_trasera_login.style.display = "block";
        caja_trasera_login.style.opacity = "1";
    }
}

