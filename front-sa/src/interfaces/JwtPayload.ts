export interface JwtPayload {
  sub: string;
  role: "ROLE_ADMIN" | "ROLE_MEDICO";
  iat: number;
  exp: number;
}