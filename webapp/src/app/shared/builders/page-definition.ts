import {Button} from '../models/config/button';

export interface PageDefinition {
  title: string,
  subtitle?: string;
  showSearch?: boolean;
  actions: Button[];
}

