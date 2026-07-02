import axios from "axios";
import type { Medico } from "../interfaces/Medico";
import type { Pacientes } from "../interfaces/Pacientes";
import type { Consulta } from "../interfaces/Consulta";
import type { ConsultaRequest } from "../interfaces/ConsultaRequest";

const api = axios.create({
  baseURL: "http://localhost:8080",
});


export async function getMedicoByEmail(email: string) {
  const response = await api.get("/medicos/email", {
    params: { email },
  });

  return response.data;
}

export async function getPacientes(params?: {
  cpfPaciente?: string;
  nomePaciente?: string;
}) {
  const response = await api.get("/pacientes", { params });
  return response.data;
}

export async function getMedicos(params?: {
  nome?: string;
}) {
  const response = await api.get("/medicos", { params });
  return response.data;
}

export async function getPacientesById(id: string | number) {
  const response = await api.get(`/pacientes/${id}`);
  return response.data;
}

export async function getSintomas() {
  const response = await api.get("/sintomas");
  return response.data;
}

export async function getSintomaById(id: number) {
  const response = await api.get(`/sintomas/${id}`);
  return response.data;
}

export async function getDoencas(params?: { nome?: string; isHereditaria?: boolean }) {
  const response = await api.get("/doencas", { params });
  return response.data;
}

export async function getDoencaById(id: number) {
  const response = await api.get(`/doencas/${id}`);
  return response.data;
}

export async function getConsultasAdmin() {
  const response = await api.get("/consultas/admin/consultas");
  return response.data;
}

export async function getConsultasMedico() {
  const response = await api.get("/consultas");
  return response.data;
}

export async function getConsultaById(id: number) {
  const response = await api.get(`/consultas/${id}`);
  return response.data;
}

export async function getTopDoencas() {
  const response = await api.get("/admin/dashboard/top-doencas");
  return response.data;
}

export async function getRankingMedicos() {
  const response = await api.get("/admin/dashboard/top-medicos");
  return response.data;
}

export async function postConsulta(data: ConsultaRequest) {
  return api.post("/consultas", data);
}

export async function deleteConsulta(id: number) {
  const response = await api.delete(`/consultas/${id}`);
  return response.data;
}

export async function postMedico(medico: Omit<Medico, "id">) {
  await api.post<Medico>("/medicos", medico);
}

export async function loginAuth(auth: { email: string; password: string }) {
  const response = await api.post("/auth/login", auth);
  return response.data;
}

export async function removerAdmin(id: number) {
  const response = await api.put(`/admin/usuario/${id}/remover-admin`);
  return response.data;
}

export async function tornarAdminSemana(id: number) {
  const response = await api.put(`/admin/usuario/${id}/admin-semana`);
  return response.data;
}

export async function tornarAdminMes(id: number) {
  const response = await api.put(`/admin/usuario/${id}/admin-mes`);
  return response.data;
}

export async function tornarAdminPermanente(id: number) {
  const response = await api.put(`/admin/usuario/${id}/admin-permanente`);
  return response.data;
}


api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});