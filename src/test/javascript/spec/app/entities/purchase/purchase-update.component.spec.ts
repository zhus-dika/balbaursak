/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PurchaseUpdateComponent from '@/entities/purchase/purchase-update.vue';
import PurchaseClass from '@/entities/purchase/purchase-update.component';
import PurchaseService from '@/entities/purchase/purchase.service';

import ProductService from '@/entities/product/product.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Purchase Management Update Component', () => {
    let wrapper: Wrapper<PurchaseClass>;
    let comp: PurchaseClass;
    let purchaseServiceStub: SinonStubbedInstance<PurchaseService>;

    beforeEach(() => {
      purchaseServiceStub = sinon.createStubInstance<PurchaseService>(PurchaseService);

      wrapper = shallowMount<PurchaseClass>(PurchaseUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          purchaseService: () => purchaseServiceStub,

          productService: () => new ProductService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.purchase = entity;
        purchaseServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(purchaseServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.purchase = entity;
        purchaseServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(purchaseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
