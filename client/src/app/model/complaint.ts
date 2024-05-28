export interface Complaint {
    id: number;
    name: string;
    email: string;
    department: string;
    query: string;
    otherQuery?: string;
    computerIp?: string;
    phone: string;
    note: string;
    date: string;
    status: string;
    flag: string;
}

export interface complaintData {
    query: string;
    otherQuery?: string;
    computerIp?: string;
    note: string;
    department: string;
}