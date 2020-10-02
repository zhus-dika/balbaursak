import { IRequestpoint } from '@/shared/model/requestpoint.model';

export interface IRequest {
  id?: number;
  created?: Date;
  customer?: string;
  phone?: string;
  total?: number;
  complete?: string;
  sales?: number;
  address?: string;
  requestpoints?: IRequestpoint[];
}

export class Request implements IRequest {
  constructor(
    public id?: number,
    public created?: Date,
    public customer?: string,
    public phone?: string,
    public total?: number,
    public complete?: string,
    public sales?: number,
    public address?: string,
    public requestpoints?: IRequestpoint[]
  ) {}
}
