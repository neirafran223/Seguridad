document.addEventListener("DOMContentLoaded", () => {
  const formEvento = document.getElementById("formEvento");
  const listaEventos = document.getElementById("eventosList");
  const detalleEvento = document.getElementById("detalleEvento");
  const detalleContenido = document.getElementById("detalleContenido");
  const toggleBtn = document.getElementById("toggleEventosBtn");
  const eliminarBtn = document.getElementById("eliminar");
  const loader = document.getElementById("loader");
  const submitBtn = formEvento.querySelector("button[type='submit']");

  let visible = false;
  let empleadoActualId = null;
  let modoEdicion = false;

  function obtenerEmpleados() {
    fetch("http://localhost:6060/api/v1/empleados")
      .then((res) => res.json())
      .then((empleados) => {
        listaEventos.innerHTML = "";
        empleados.forEach((empleado) => {
          const li = document.createElement("li");
          li.innerHTML = `
            <h3>${empleado.nombreEmpresa}</h3>
            <p>Personal: ${empleado.cantPersonal} | Seguridad: ${empleado.tipoSeguridad}</p>
            <button onclick="verDetalle(${empleado.id})">Ver Detalle</button>
          `;
          listaEventos.appendChild(li);
        });
      })
      .catch((err) => {
        console.error("Error al obtener empleados:", err);
      });
  }

  window.verDetalle = function (id) {
    empleadoActualId = id;
    fetch(`http://localhost:6060/api/v1/empleados/${id}`)
      .then((res) => res.json())
      .then((empleado) => {
        detalleEvento.style.display = "block";
        listaEventos.style.display = "none";
        detalleContenido.innerHTML = `
          <h3>${empleado.nombreEmpresa}</h3>
          <p><strong>Cantidad de Personal:</strong> ${empleado.cantPersonal}</p>
          <p><strong>Tipo de Seguridad:</strong> ${empleado.tipoSeguridad}</p>
          <button onclick="editarEmpleado(${empleado.id})">Editar</button>
        `;
      })
      .catch((err) => {
        console.error("Error al obtener detalles del empleado:", err);
      });
  };

  window.editarEmpleado = function(id) {
    modoEdicion = true;
    empleadoActualId = id;
    submitBtn.textContent = "Actualizar Empleado";

    fetch(`http://localhost:6060/api/v1/empleados/${id}`)
      .then((res) => res.json())
      .then((empleado) => {
        document.getElementById("nombre").value = empleado.nombreEmpresa;
        document.getElementById("cantPersonal").value = empleado.cantPersonal;
        document.getElementById("tipoSeguridad").value = empleado.tipoSeguridad;

        detalleEvento.style.display = "none";
        listaEventos.style.display = "none";
        formEvento.scrollIntoView({ behavior: 'smooth' });
      })
      .catch((err) => {
        console.error("Error al cargar empleado para edición:", err);
      });
  };

  eliminarBtn.addEventListener("click", () => {
    if (!empleadoActualId) return;

    if (!confirm("¿Estás seguro de que deseas eliminar este empleado?")) return;

    loader.style.display = "block";

    fetch(`http://localhost:6060/api/v1/empleados/${empleadoActualId}`, {
      method: "DELETE"
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("Error al eliminar el empleado");
        }
        return response;
      })
      .then(() => {
        loader.style.display = "none";
        alert("Empleado eliminado correctamente");
        volver();
        if (visible) obtenerEmpleados();
      })
      .catch(error => {
        loader.style.display = "none";
        console.error("Error:", error);
        alert("Ocurrió un error al eliminar el empleado");
      });
  });

  window.volver = function () {
    empleadoActualId = null;
    detalleEvento.style.display = "none";
    listaEventos.style.display = visible ? "block" : "none";
  };

  toggleBtn.addEventListener("click", () => {
    visible = !visible;
    listaEventos.style.display = visible ? "block" : "none";
    toggleBtn.innerText = visible ? "Ocultar Empleados" : "Mostrar Empleados";
    if (visible) obtenerEmpleados();
  });

  formEvento.addEventListener("submit", (event) => {
    event.preventDefault();
    loader.style.display = "block";

    const empleadoData = {
      nombreEmpresa: document.getElementById("nombre").value,
      cantPersonal: parseInt(document.getElementById("cantPersonal").value),
      tipoSeguridad: document.getElementById("tipoSeguridad").value
    };

    const url = modoEdicion
      ? `http://localhost:6060/api/v1/empleados/${empleadoActualId}`
      : "http://localhost:6060/api/v1/empleados";

    const method = modoEdicion ? "PUT" : "POST";

    fetch(url, {
      method: method,
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(empleadoData)
    })
      .then((res) => {
        if (!res.ok) throw new Error("Error en la solicitud");
        return res.json();
      })
      .then(() => {
        loader.style.display = "none";
        formEvento.reset();
        submitBtn.textContent = "Crear Empleado";
        modoEdicion = false;
        empleadoActualId = null;
        if (visible) obtenerEmpleados();
      })
      .catch((err) => {
        loader.style.display = "none";
        console.error("Error al guardar empleado:", err);
        alert("Ocurrió un error al guardar el empleado");
      });
  });
});
