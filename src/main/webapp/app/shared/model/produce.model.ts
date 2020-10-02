import { ICategory } from '@/shared/model/category.model';
import { IFeedback } from '@/shared/model/feedback.model';

export interface IProduce {
  id?: number;
  name?: string;
  unit?: string;
  contains?: any;
  days?: number;
  description?: any;
  price?: number;
  fileContentType?: string;
  file?: any;
  category?: ICategory;
  feedbacks?: IFeedback[];
}

export class Produce implements IProduce {
  constructor(
    public id?: number,
    public name?: string,
    public unit?: string,
    public contains?: any,
    public days?: number,
    public description?: any,
    public price?: number,
    public fileContentType?: string,
    public file?: any,
    public category?: ICategory,
    public feedbacks?: IFeedback[]
  ) {}
}
