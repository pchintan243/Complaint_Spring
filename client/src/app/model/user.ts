export interface UserForRegister {
    name: string;
    email: string;
    password: string;
    phone: number;
}
export interface UserForLogin {
    userName: string;
    password: string;
    token: string;
}
