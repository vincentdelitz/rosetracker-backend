using leonardo from '../db/data-model';
using blockchain from '../blockchain/data-model-blockchain';

service CatalogService {
  entity Products as projection on leonardo.Products;
  entity Package as projection on blockchain.Package;
}
