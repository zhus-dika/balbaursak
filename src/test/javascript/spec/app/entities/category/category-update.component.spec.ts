/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CategoryUpdateComponent from '@/entities/category/category-update.vue';
import CategoryClass from '@/entities/category/category-update.component';
import CategoryService from '@/entities/category/category.service';

import ProduceService from '@/entities/produce/produce.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Category Management Update Component', () => {
    let wrapper: Wrapper<CategoryClass>;
    let comp: CategoryClass;
    let categoryServiceStub: SinonStubbedInstance<CategoryService>;

    beforeEach(() => {
      categoryServiceStub = sinon.createStubInstance<CategoryService>(CategoryService);

      wrapper = shallowMount<CategoryClass>(CategoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          categoryService: () => categoryServiceStub,

          produceService: () => new ProduceService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.category = entity;
        categoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(categoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.category = entity;
        categoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(categoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
