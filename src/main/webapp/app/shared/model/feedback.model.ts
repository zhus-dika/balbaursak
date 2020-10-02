import { IProduce } from '@/shared/model/produce.model';

export interface IFeedback {
  id?: number;
  created?: Date;
  customer?: string;
  vote?: number;
  content?: string;
  produce?: IProduce;
}

export class Feedback implements IFeedback {
  constructor(
    public id?: number,
    public created?: Date,
    public customer?: string,
    public vote?: number,
    public content?: string,
    public produce?: IProduce
  ) {}
}
