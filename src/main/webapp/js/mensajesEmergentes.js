/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//Este mensaje funciona para marcar mensaje de actualizacion de Informacion de Usuario
function showAlertDelete(){
    event.preventDefault();
    Swal.fire({
        title: "¿Estas seguro de eliminación la información?",
        text: "Esta accion eliminara la información relacionada al registro seleccionado. ",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si",
        cancelButtonText: "No"
      }).then((result) => {
        if (result.isConfirmed) {
            document.querySelector("form").submit();
        }
      });
}

//Este mensaje funciona para marcar mensaje de actualizacion de Informacion de Usuario
function showAlertUpdate(){
    event.preventDefault();
    Swal.fire({
        title: "¿Estas seguro de actualizar la información?",
        text: "Esta accion solo modificara los datos actuales",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si",
        cancelButtonText: "No"
      }).then((result) => {
        if (result.isConfirmed) {
            document.querySelector("form").submit();
        }
      });
}

//Este mensaje funciona para marcar mensaje de insercion de Informacion de Nuevo Usuario
function showAlertAdd(){
    const form = document.querySelector(".form_register");
    if(!form.checkValidity()){
        return;
    }

    else{
        event.preventDefault();
        Swal.fire({
            title: "¿Estas seguro de dar de alta la información?",
            text: "Para corregir algun dato o dar de baja, contactar al departamento de CELe",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Si",
            cancelButtonText: "No"
          }).then((result) => {
            if (result.isConfirmed) {
                form.submit();
            }
          });
    }
    
}
