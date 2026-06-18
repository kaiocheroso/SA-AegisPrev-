import axios from "axios";
import type { Usuario } from "../interfaces/Usuario";
import type { Pacientes } from "../interfaces/Pacientes";
import type { Consulta } from "../interfaces/Consultas";

const api = axios.create({
  baseURL: "http://localhost:3001",
});

export async function postUsuario(usuario: Omit<Usuario, "id">) {
  await api.post<Usuario>("/Usuario", usuario);
}
export async function postPacientes(pacientes: Omit<Pacientes, "id">) {
  await api.post<Pacientes>("/Pacientes", pacientes);
}

export async function postConsulta(consulta: Omit<Consulta, "id">) {
  await api.post<Consulta>("/Consulta", consulta);
}
