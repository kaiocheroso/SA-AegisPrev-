import axios from "axios";
import type { Usuario } from "../interfaces/Usuario";
import type { Pacientes } from "../interfaces/Pacientes";
import type { Consulta } from "../interfaces/Consulta";

const api = axios.create({
  baseURL: "http://localhost:3001",
});

export async function getPacientes() {
  const response = await api.get("/Pacientes");
  return response.data;
}
export async function getPacientesById(id: string | number) {
  const response = await api.get(`/Pacientes/${id}`);
  return response.data;
}
export async function postUsuario(usuario: Omit<Usuario, "id">) {
  await api.post<Usuario>("/Usuario", usuario);
}

export async function postPacientes(pacientes: Omit<Pacientes, "id">) {
  await api.post<Pacientes>("/Pacientes", pacientes);
}

export async function postConsulta(consulta: Omit<Consulta, "id">) {
  await api.post<Consulta>("/Consulta", consulta);
}

export async function deletePaciente(id: string | number) {
  const response = await api.delete(`/Pacientes/${id}`);
  return response.data;
}

export async function editarPaciente(
  id: number,
  paciente: Omit<Pacientes, "id">,
) {
  const response = await api.put(`/Pacientes/${id}`, paciente);
  return response.data;
}
