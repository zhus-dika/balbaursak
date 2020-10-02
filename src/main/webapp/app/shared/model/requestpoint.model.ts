import { IProduce } from '@/shared/model/produce.model';
import { IRequest } from '@/shared/model/request.model';

export interface IRequestpoint {
  id?: number;
  quantity?: number;
  total?: number;
  produce?: IProduce;
  request?: IRequest;
}

export class Requestpoint implements IRequestpoint {
  constructor(public id?: number, public quantity?: number, public total?: number, public produce?: IProduce, public request?: IRequest) {}
}
