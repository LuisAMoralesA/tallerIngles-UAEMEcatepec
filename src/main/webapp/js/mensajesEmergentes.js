/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//Este mensaje funciona para marcar mensaje de actualizacion de Informacion de Usuario
function showAlertDelete(formulario){
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
            formulario.submit();
        }
      });
}

function showAlertClear(event, url){
    event.preventDefault();
    Swal.fire({
        title: "¿Estas seguro de limpiar los registros?",
        text: "Esta accion conservara los registros de alumnos, pero limpiara las listas de calificaciones y de pago. \n\
              Asegure de realizar un respaldo de la información.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si",
        cancelButtonText: "No" 
      }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = url;
        }
      });
}

//actualizarInformacion.jsp y actualizarUsuario.jsp
function showAlertUpdateInformation(){
    event.preventDefault();
    Swal.fire({
        title: "¿Estas seguro de actualizar la información?",
        text: "Esta accion modificara la información correspondiente a este usuario",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si",
        cancelButtonText: "No"
      }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("formUpdateInformation").submit();
        }
      });
}

//calendario.jsp
function showAlertUpdateCalendar(){
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
            document.getElementById("formUpdateCalendar").submit();
        }
      });
}

//calendario.jsp
function showAlertUpdatePeriodo(){
    event.preventDefault();
    Swal.fire({
        title: "¿Esta seguro de actualizar el periodo escolar?",
        text: "Al confirmar esta información, la informacion actual pasaran a corresponder al nuevo periodo escolar. \n\
                Podra regresar o cambiar de periodo desde esta misma opción",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si",
        cancelButtonText: "No"
      }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("formUpdatePeriod").submit();
        }
      });
}

//seguimientoPago.jsp
function showAlertUpdatePayment(){
    event.preventDefault();
    Swal.fire({
        title: "¿Estas seguro de actualizar el seguimiento de Pago?",
        text: "Los registros pueden ser modificados en cualquier momento",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si",
        cancelButtonText: "No"
      }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("formUpdatePayment").submit();
        }
      });
}

//vistaCalificaciones.jsp
function showAlertUpdateGrade(){
    event.preventDefault();
    Swal.fire({
        title: "¿Estas seguro de actualizar las calificaciones parciales?",
        text: "El sistema calculara un promedio tomando en cuenta la información actual",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si",
        cancelButtonText: "No"
      }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("formUpdateGrade").submit();
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
