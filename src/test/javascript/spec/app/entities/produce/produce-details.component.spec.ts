/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProduceDetailComponent from '@/entities/produce/produce-details.vue';
import ProduceClass from '@/entities/produce/produce-details.component';
import ProduceService from '@/entities/produce/produce.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Produce Management Detail Component', () => {
    let wrapper: Wrapper<ProduceClass>;
    let comp: ProduceClass;
    let produceServiceStub: SinonStubbedInstance<ProduceService>;

    beforeEach(() => {
      produceServiceStub = sinon.createStubInstance<ProduceService>(ProduceService);

      wrapper = shallowMount<ProduceClass>(ProduceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { produceService: () => produceServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProduce = { id: 123 };
        produceServiceStub.find.resolves(foundProduce);

        // WHEN
        comp.retrieveProduce(123);
        await comp.$nextTick();

        // THEN
        expect(comp.produce).toBe(foundProduce);
      });
    });
  });
});
