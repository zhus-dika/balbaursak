/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CategoryDetailComponent from '@/entities/category/category-details.vue';
import CategoryClass from '@/entities/category/category-details.component';
import CategoryService from '@/entities/category/category.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Category Management Detail Component', () => {
    let wrapper: Wrapper<CategoryClass>;
    let comp: CategoryClass;
    let categoryServiceStub: SinonStubbedInstance<CategoryService>;

    beforeEach(() => {
      categoryServiceStub = sinon.createStubInstance<CategoryService>(CategoryService);

      wrapper = shallowMount<CategoryClass>(CategoryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { categoryService: () => categoryServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCategory = { id: 123 };
        categoryServiceStub.find.resolves(foundCategory);

        // WHEN
        comp.retrieveCategory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.category).toBe(foundCategory);
      });
    });
  });
});
