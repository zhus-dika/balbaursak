import { IProduce } from '@/shared/model/produce.model';

export interface ICategory {
  id?: number;
  name?: string;
  produces?: IProduce[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public produces?: IProduce[]) {}
}
