package dev.lopyluna.create_d2d.client;

import dev.lopyluna.create_d2d.client.layers.DnDesireCosmeticLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static dev.lopyluna.create_d2d.DesiresCreate.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class PlayerCosmetics {
    public static String[] DnDesireCosmetic = {
            "d02304fc-3fec-42b1-8423-bf4f024c0439", "3cf02ab7-caf2-4fc4-931d-8843b16ebd99", "7e28ea18-f645-444e-b3b6-e4dd08d777b1", "b1b9d693-7c5f-4df0-b5a6-d00f22c448d1",
            "9dc59b94-cf00-413e-b828-407f991d203a", "f89c4b55-8e3c-4605-8864-f92d8affc66b", "a0994950-c71f-4980-9d67-723a6fbeed83", "64b932f9-24cb-4621-9f9a-1fa3fbc774f2",
            "62f779fc-db67-483e-8670-d5c4f038c31c", "7a3b9207-49ba-47bc-8f4f-9b52c2195d87", "e69fd200-2269-410b-8d0c-e5d173519d9c", "f9973e16-4f2c-4830-a54e-35b583713414",
            "a086fc39-8c1d-4001-9544-6f92ab57f0fc", "0a51501b-de03-4d34-9868-bbf6cceda788", "325aeaa4-12e8-4186-9ea3-720743c605f7", "3724a61a-0299-4df7-8656-1997482c474c",
            "718126fa-3454-4d01-8eae-f9739350ac7d", "12a89a4b-82b0-4b4f-a0d2-357771d74a81", "2f50758e-0a01-4d20-8fd5-f28474155ced", "e32aed66-d2a3-4ef0-abda-0db94a0fea97",
            "1acd7f2e-de75-468d-a5c3-9229169b8db0", "8313c5eb-4731-4f2f-ae6c-eddb0946bb44", "9a9e1d61-b165-416a-bd94-ce3f6cd8a425", "9c7f73dc-f0d5-4776-95a4-373a9829079b",
            "1308160b-f676-425e-840d-2ac0b0c869fb", "03090011-ed59-4a23-bc2f-103f11e60186", "18cd3484-2f0a-4a05-b067-0cdd162476cc", "c2e01133-8a2f-4014-945b-b18b907afea9",
            "a121ea21-660a-46c0-a6ae-fc36208fb1d2", "cb7ea0ce-52f1-48f5-bd85-98740c328693", "72433b3f-a8fe-4975-8c5a-d860b0db18b5", "33bcc4df-b0c1-454c-b9d5-98eef3ec6a42",
            "4ef58af4-56b9-488d-ad65-2db20861beb1", "645ce6ab-8cce-4515-904d-fd1718c9e4e4", "0f375899-0629-49bb-8252-8a0428a155b7", "4288b347-ce98-4a8b-b0b4-1093b7f8bca6",
            "874656eb-467c-4297-9a86-b7fbfbadf1f9", "64467ce0-b96a-4cbd-b368-304cc8aba1ed", "0bcbddb1-7f71-4516-90d3-5ffe77f5c76e", "b067642d-6ff1-4e97-8773-6a0baf47cba4",
            "3d8e6e5d-b96a-4655-89cd-456c178c5ad4", "98f9241d-7871-4c80-939a-071fbd9db2bb", "0b57e599-5194-4ff4-937b-949d4170d519", "726a8e5f-d46d-4bc9-8c7f-b0f8cc54c2e7",
            "8e7e2979-2b77-476c-9934-6c8444d907bd", "eed3d8a0-a945-4bb5-aa3f-109d9850b437", "f1956851-9928-449c-aefd-9c91495dfcfb", "8a173564-6440-45cf-b6e3-3a8c6c6c0d1a",
            "3c0fbd2e-8a5f-454e-8d9c-607d608c0362", "44b1a9a3-0547-4830-8ccb-9374b860f5e0", "503340aa-7ba4-44d0-ae03-8b72bfcba660", "ca7c7780-e25a-4b9b-986d-c039bab98348",
            "8128e092-2970-4caf-9350-524a3b0be5ee", "72fed82a-a4e5-40d7-983c-f93d5f25f515", "5b066d87-20f5-43dc-876e-e30f82b06d0e", "97712a5f-af2b-49d7-90d5-a397983ffee8",
            "15312dcd-1cc0-44e6-8da4-08e98905a2e8", "a418e39c-fba6-4042-bfaa-795437b34711", "557e3986-0e60-4fe8-8a63-cc852be68657", "fb61d152-9f1e-42e7-ae06-33a4634076f0",
            "32a8a15d-d97d-4180-a64b-fd4fd5b10078", "67f93d44-14dd-40d7-b61c-8dd641413776", "957a7c36-e6d5-4944-a23f-5e6c47961f0a", "564cd919-995d-4a13-980f-59f762fd8336",
            "9dd94f95-4f00-4e3e-9518-b4596a5bb07e", "d311b903-7693-40ee-b9bd-3f9b15bae6d3", "36f7de69-0df2-44b9-8090-1356cce20131", "11f47466-39b8-49f2-a01e-1ecbac4b0532",
            "a1a0c225-fec1-4aa2-9b80-9e18114dca0f", "49aab5a6-a6e1-49e5-942b-0a087342540c", "94c78580-5302-4961-9236-69c7f6ae4eb7", "efd7ac8e-5506-4b9d-a1b3-afa21786794a",
            "2eb803a5-2d13-43eb-a3c0-b715990f7f9c", "b62ad4bd-b2eb-47cb-b88b-564fc8ffb50f", "a2bfbc53-eb4a-49e9-a7c6-b4be31757e13", "f1e4fdd5-2d14-4112-af2d-00914cdab062",
            "e54cc7b7-6e27-407d-a395-3ca6d5e23df4", "aa0de894-7d3b-4d47-b1a5-63abffb5f8f5", "618ce8d8-3f67-4043-a4e8-55353e5d7a27", "7af1ecfc-497f-4e2a-81ce-978f94b6dbde",
            "4a4e5cf2-7791-4a53-8245-55962f1ae99b", "ec83fe0b-c1c7-4b20-8644-5429b9b0222d", "73dcf400-c248-4014-8a34-53c6038e2e3b", "25659b5d-3edc-4d00-b3ef-b91464dc046f",
            "f47c8400-e502-404f-87e9-f972c2c6b7aa", "a25b9dfd-12ea-41d5-b8f0-95ee9d6311ce", "bbb4fbf8-5ef5-4ab3-abc1-0ba846b2548f", "50910f9f-333a-44bf-9a61-4478528024c5",
            "63b0396c-2312-4aba-984f-eda4075bd2a7", "62b5619d-7f83-4d77-8de0-0b21b0481739", "61faa57e-febe-4ff1-ba0f-16970806e913", "7787c2e5-c5ab-4b7c-a934-b9c079323412",
            "88887704-cf79-4f0d-afb0-af1680983db3", "1c9c5fc6-f78b-47ba-8bba-7e120c2dc88f", "4bbddd81-fa87-43c2-89b4-3bfad0e67b2f", "5941fd46-4559-4ebc-9d5c-a17a07d16865",
            "00f61bdb-0079-4823-9bb3-90c7d95aa3c9", "eb38f667-21a1-40ca-9f0d-49b0fbeef430", "6a699610-eab8-4243-b71a-d3becc4d0753", "28e7b518-4a19-49ac-a152-04f5c1d67f1b",
            "810b2dea-c200-4221-96b4-75bcb6c54cca", "c6ef7170-06a6-4c5c-96cb-b4cba7815732", "aa0b6e36-eadf-4d79-a95b-f05e3d9e60b3", "d21494ea-d23a-4097-b930-b77d3079940f",
            "509db874-b873-42e4-bf59-6b4a0bb5b599", "4c67af8f-7ee1-4224-9ef0-fe13259011d1", "5cc9737e-804c-4afa-9cd1-39f4cd2575ce", "9d5ff129-6741-4b23-8a70-8ab597078b7e",
            "af1aafdc-954b-4580-a7d9-000cabed0cd1", "030ab932-2188-44cf-9a83-2d595fcefe08", "dab48452-6d86-403e-9bb5-3dfe2fdeb82b", "be8e6bf6-421d-49c6-8ed8-5215a46ff88c",
            "82205d59-88dc-4278-82f3-70a7c403947a", "aafc4c6c-39c6-4a1a-b2f1-d3863270dcc1", "48b1d29a-1f5f-4a5c-b926-c446b1b7617a", "ccdff279-77b1-4787-a524-cdbdb3ae18a0",
            "4c0ececa-16b2-4409-a6d9-174bb5b2fb31", "b92d5929-0581-446b-b411-5b83315cc260", "747a667d-8352-472b-b15a-43e79ae4a11a", "270bedbf-d7d5-49e9-8ef1-cc77620732d8",
            "8d222347-47d9-4272-adf3-9f0e7f63e06b", "1559c347-4d97-47f3-b01c-182267ea0e26", "b18773d4-f61e-481f-b2b7-7fd419fe7279", "9e391423-bf09-4652-bc8c-800f31a8764d",
            "56539c1d-38fe-4181-9f08-085a5951d560", "67397179-dce3-4490-be20-cae3317669d3", "c43942fc-65e6-451c-9d8c-06dfc4255eea", "caeb53d0-6845-4a59-8963-68c29727e339",
            "f563f2bc-c8a3-4a61-b41f-6460d2a004d8", "b7697f51-7744-4789-93b3-abd50008d9d7", "200ad3ed-5dd7-4dd3-a400-87cd877e5bed", "85771d2f-faf4-4761-9c00-152168a12c06",
            "1b933225-3927-4d0f-90ea-bf946b74bf75", "616548c9-c83e-444d-b58d-e754f8cda85a", "a9f36d7a-d0c9-4d5c-85e2-df748899b47d", "90a68cbf-8cb3-476f-b531-48cd81ae681e",
            "4c307ed4-516a-4b2f-9cee-154c03c9285c", "9914a23c-e274-4489-999a-db920630904c", "85ee4df2-9239-4013-8083-d1a3597541d7", "f58f3090-036d-44d9-a1f7-d23857b2d527",
            "9d723303-b4a6-48c2-8653-a2a35c3aa5bf", "82ea96a2-e317-4442-82e1-25f8bbde9db5", "a985c23f-a7e9-4e22-be94-4b98afe14f73", "8091c1df-ac06-4b87-b12d-7e10fe2df1b6",
            "7062c40e-c19d-4567-90e6-8a8156285f82", "c08e42dc-9848-410f-830a-6b26c99577f8", "84e11991-e9fa-45a2-90c1-01344f56cad1", "d230874b-0514-4c55-8277-c5070d445eec",
            "3e296a7b-617a-4851-8241-1339620be82f", "ccade125-2d93-4e15-9230-21dfa45d2d12", "1af00df2-048f-4a8a-9f88-79ee60eecd32", "51a8f25b-747c-4a68-a10b-f2eeed21d1ce",
            "a73a063d-060f-4f82-b7c2-9d0551cf915e", "e0685ea5-7046-42a5-a2ba-8ba545af96da", "422d9c81-f98a-4ad2-9cb7-8adaf4e76275", "3b2a65a0-7478-4b4a-b84e-f79e5831c98f",
            "68cde10c-7a9c-414a-b0e0-b0c92b31408d", "53254991-28b7-4f64-980e-01acdad607d7", "15abf0b4-2453-44ed-805d-6b46e5d352ca", "8e78279e-c4ce-42ae-98e2-4612cc1db801",
            "e167f3fe-1cc7-43aa-a558-cdd0babd44ce", "64b0db7c-2936-400e-8052-b5e479dff621", "52aea6bd-21f2-4eeb-8323-7669155c636f", "3b782e04-2d70-4550-bab4-1d0a37b7f9f4",
            "d73dfd9d-610c-4826-ae2c-16b9f37a6f22", "8fe1b12a-07e9-4e1d-aa41-1274b6b6bfbd", "fd7139ca-bc88-4d23-81c8-3bfefd073bb9", "0bad7ef7-5b4d-481e-8847-34da81098279",
            "0f5e98d8-d435-4d28-a78f-f3d0dafae633", "10679430-c1b4-4700-bd27-ca62742757fd", "08e81c68-0a2f-4625-882b-ddcf3c86e351", "579dc0ae-019d-42db-8c71-7d903ed553f0",
            "bea56b09-adb3-4da8-831a-0016b56319a7", "cadeeeb8-a330-4898-aaa1-6c08b9e86cfc", "ba1a7140-aacf-44f3-8f75-49dc45de61f7", "e595f459-9247-4038-b947-f6b4dfc43ed5",
            "f43a08b7-6223-4c5a-96e3-2955ccf4e7b4", "4076154c-376a-40e5-9268-8557c87006e6", "1389f8ce-9ba3-4cff-b87a-b8d905fcdbe7", "71490a21-9ffb-4343-9d78-36bd30ceb218",
            "e76c418b-4bf6-4206-aeeb-6dffada1a1ca", "f9c6abbb-9a43-43f0-a68d-648b9fa2c571", "c64520b1-fb58-4ef1-930d-32ba04822842", "a1574f5f-33a0-46ec-953c-afb323bd7642",
            "386ddb0e-caf6-4b92-9042-401f3952a37e", "c6f9e2be-8dff-4fd9-9f5b-6fb207bbffee", "587f73a4-b5b2-4984-9980-2703527d75c4", "aab4a069-6bc9-44b1-8341-c8c4dfbc05ff",
            "46866dd4-dda6-41a2-bff5-cf8be6ddb8dd", "23961781-7002-4f25-aa14-a889710e69b3", "82aef787-a0ec-457e-94e3-ebd1af798c3b", "b91ec83b-bd0d-46b0-ae54-63105e85ad15",
            "416fc916-69cc-4b3c-8c5e-a39a5acb6981", "716574d4-291c-4563-860a-d949ab4f8460", "1b18d456-6041-419a-9625-824768fa19b4", "8953d78e-55e0-41e9-9296-ae1f7eeb35f8",
            "5d89ac81-94f8-4502-b5bd-934f7bc976e6", "0fb2f2fe-ce5c-4fd1-92d8-60abda7167ac", "b28f0bb3-6547-451f-a94c-8bf6508e70e3", "7ac82bda-106f-4733-a9ad-4e9ba88f704a",
            "782c820e-58f5-4390-9eb4-aaec5c165ccf", "31c57ff3-9bd1-451c-ad0b-a69527b30990", "299cb98e-0f88-4093-ad14-4289961b9d6c", "41a77af8-78dc-49f1-9df2-74fedd9c0aae",
            "654c1378-958a-468d-9832-ec4a51f94f95", "3085ddd4-8c9d-4630-82b9-40a6fd60a983", "dc815602-2759-4b34-9008-9a40d6de2649", "20d24af6-8026-4a20-89db-12fae7111ebd",
            "e2cfd825-08ad-4437-bcaa-2940272fbdbf", "d8f1d113-dd2b-4e4d-8726-7e788e660497", "1aadf207-3d5f-42fd-846c-0d6da6f4bdbb", "840bc47e-c93a-4130-81c6-350be3a8c76a",
            "2e714d0e-4b93-402c-8ff0-b477d2fa9004", "0ac41c2d-3608-4c8f-9958-3b5b0ff1689a", "f349da1c-9cda-4fb3-9862-aaa4d2f1f943", "bbcca958-cb08-4aa8-be63-853d083aa4b4",
            "0ae09d3c-b6d2-4cfa-b43f-f2273840a166", "a1732122-e22e-4edf-883c-09673eb55de8", "02480fb1-37ca-4deb-8047-99dee3bc71b4", "c37e83d9-3655-4c48-a173-3c5a7ee7760c",
            "c6775d13-b5c3-4988-b510-c7fd218668b8", "0905afa3-e403-4b5b-9f7a-e898c24dc4ae", "b2d3c5df-c826-4c7d-8c5f-2eb3098d7ada", "0e08ec92-133e-4707-8f4a-28ab0b6d3403",
            "b654d42d-5f9c-4b3a-883f-aa69d5d51f38", "e3f818fb-7a8d-4d4b-81ff-3936ba9db322", "2b7bd053-df04-4dce-8b29-fc6c6ab2899c", "b4067926-a8b5-4c03-a2d7-db42da65fb95",
            "49e7e582-32b4-444a-a54f-29364aeadcbe", "b0835450-d0af-4f71-a888-9eba8d312501", "5a54f320-65fe-45f8-bc8a-a07482697bb5", "592a4d2c-9f9e-4e4b-befa-c992287a19fd",
            "8453ee38-fd3f-4d5d-a1ff-2d12460644a4", "ce83f1f4-5539-42ed-9d6f-ba376066ede5", "39eaeb8a-5f06-42f8-bf59-227cc32d3444", "61c67d76-6b06-4718-8fbd-1b0e5f70d955",
            "cc740a50-b492-49e6-b81f-a16788d09e29", "4632a60d-d939-4a98-b221-3c69fbbfa6c5", "557e3986-0e60-4fe8-8a63-cc852be68657", "6a229140-5087-4253-9e23-bce509594d3b",
            "07e6b550-be92-4422-a269-345593df5a10", "af189ae5-6adb-4733-bba1-d3f3017759dc", "fee7d965-7c50-425a-b8f9-77f17dcc438b", "64f0361c-a7a8-43ec-83c4-378f8e28702c",
            "20d77cb6-c07a-496a-8bcc-cbfeaeaee264", "0e03ded1-bcae-4876-84c7-917f006926fd", "d53762f3-7f02-4e86-8a82-6100a19b3f28", "e8b46b33-3e17-4b64-8d07-9af116df7d3b",
            "f6cb90aa-4d21-47b8-89dc-0b3e9dda9d1d", "8bacc3d4-a301-429b-859d-042d47f61e4d", "1e565af3-dced-44a1-8ffb-2ed80593584a", "dc0c6799-1e62-4d45-9698-1560128b7361",
            "eb01be50-6e7c-4ea9-a5d4-43dbc5338f5b", "56539c1d-38fe-4181-9f08-085a5951d560", "e1ce2677-aba3-43a1-ab46-10605fe39efb", "17554562-2b0a-4dc9-85e8-b3da7e5f74ed",
            "c1a77bc3-c5c6-4e06-b805-638f818c433c", "249a587a-033c-4585-bc46-142f9682172b", "f075157f-8532-4a15-9d2d-a523110fc676", "df85ef11-70cb-4671-95ee-abb1cb0856a6",
            "2cb8e072-3198-42e2-84ca-778ca6d44e67", "67607323-4c10-439e-8095-d5917a4601bd", "45480173-d50e-4583-b142-28eaa017e946", "b8e70fbd-8ac5-4313-8d12-58afcbe0a5c2",
            "906dbdf8-3e43-46be-ae77-49f2cf058315", "2af7a0e0-3831-4b2e-8e0f-1931cb5839c5", "2a2c6015-b479-4060-a6d3-45e08518eb3f", "51d425b4-0819-4dea-81aa-55eb78011777",
            "4cd6a0c1-a79d-479e-be53-8a0861711225", "cf21e24b-5717-4df6-83c1-bf08596992c3", "5acad853-581b-4720-b043-b77c495be163", "1e6c2f5f-6931-4f6b-97fd-da18be313f9a",
            "b8c5aee8-d25b-4d10-ae06-d5e78ee92931", "05fd2433-6cac-421b-982a-7ac849186da2", "6c6d093e-dca3-42ae-b107-2673b380332e", "1604e0a1-d64b-4395-8fe5-f67a9786b044",
            "933490ee-0820-408e-9ef7-083fb7b61b24", "0d8b2d83-106e-42f9-b86b-cce18f7520c3", "ab87beb9-7939-4cb3-b45d-674df48b17dc", "4a9c7aab-41e4-407e-a7bc-2214759e783f",
            "deecdafa-5409-47aa-a188-6af803d95194", "687066e3-a502-4244-bddb-03ecf0d17fd9", "a0640e13-b883-4077-9493-ea23ea2f13b5", "7d8aad29-35d4-491c-9a7c-d1a9c170da56",
            "1479bc1d-fdd6-4533-989a-aaa2f40fe6f1", "0a29e3e2-cb55-45a1-a6b2-0d10c1b30506", "5410a672-9083-4e73-99a0-cc92a72f386b", "9245ed80-bf42-49fb-a29a-4fad637a7484",
            "6a806740-ef86-4f6f-8b88-b189cac26b86", "bdcd4664-98e2-4481-89df-b59468eacb95", "be16bf44-e559-4077-ad2e-0dd6531f28ea", "c0b27322-20ed-49ec-9c32-742fd7221e1e",
            "1548b07b-4053-4081-8e4c-911bbd359cf5", "2437a9d4-8cf1-43b5-be2a-f9ec244a39a4", "cf1dc329-ea30-4f25-b30d-72b1cb6bc191", "e796d7a4-d9ad-4236-9fa2-eb62c917228e",
            "03dfb866-7054-466e-a0c7-69ccfe653a10", "2eceb83d-843e-4711-88c7-8c7e36d4123d", "14d57eca-5bb6-4bc8-8e55-e7872eff5b24", "dabe4270-22f2-4c67-a16f-b75a846c6f5f",
            "2583f087-c5d2-4bbe-9127-b3f098eb634e", "9043d09a-8c53-4ef1-acae-02adb65223b0", "9b5a61d0-04e3-4399-a239-5e55d1790989", "5173625e-ab41-4168-a53f-89c298c8edf5",
            "e186ba67-43bd-4e1c-8915-12a37b81b085", "f2bde2f6-bff5-46da-b7a8-2396b29ee3e6", "5ab3ba0f-e15e-4300-9535-a46ac7b14b6c", "2cee3ddb-33d7-4407-bdca-fabfd5f2160c",
            "a2cce7df-175f-4065-914e-1de03da65be9", "360c8c04-cc42-447e-be1e-d23ca3744d30", "deaaafb4-fa29-433b-ba81-68c34971bee5", "c1c3ee5a-a704-443c-be0b-4ab4cff06e13",
            "eeeb7f6e-ad37-41c0-b8e6-23ad115d3b0d", "05d626cd-e6a4-4221-a984-3c591fea5423", "314f1ede-45b3-4289-b1b2-c771d118a171", "323c1981-abc2-488c-84e3-d46682d9bc81",
            "3d338892-e9d6-4e61-a5fd-c5a432109df0", "06b71494-24f7-4aa7-a0bd-ca755f79381e", "fca3c66f-92fb-4948-b004-0c6113f30f06", "eaab7c3b-3e97-4f90-8acc-d2fd3864c9b7",
            "875f5832-2747-46b9-89e9-9763de59f5b0", "9848a39e-8bb7-4943-8be5-021c44467687", "1b26b59c-4dce-4a15-bb8d-b2852e116f46", "560fadf5-806a-4e04-a46a-5be875699bac",
            "8cc2e1f3-2ac4-4542-ac7f-6dc2e0d30bc4", "8969fe9d-bfd3-4e77-9657-a3516961f179", "69303a61-5e67-4e8f-b45b-92e305fa4987", "8e357546-86bb-4985-a97b-f90743477e78",
            "53ba6f2d-333e-4d77-97d6-f704a6171932", "86c739d9-e357-49b6-bedd-41f6df309760", "c0e65f5d-6613-49c2-9c65-3bc100a1b4e9", "23bceb4a-77cd-48fb-a2d6-a099dbe5d1c1",
            "93a036bd-620e-4346-8cec-a5b977eba311", "1d7d857a-06a1-441f-8c6a-7c60e59d171f", "7f2e368f-ef81-443c-bb71-81a8d5f9bc13", "419c53b8-c57a-4a11-a032-d69ada94ae5c",
            "cdd9cfff-1547-493f-934a-080aab27e618", "310e6e7d-6e00-432f-97a5-26ce005c5d93", "a3fff0ad-fc89-407d-b0b0-d89b0d3dce85", "abcee76a-74d7-446f-80c3-9479e0d21c75",
            "cfc268e0-c1f1-433c-bd1f-6a088143f0d6", "3d6dddf1-75f3-4253-bcc2-4e5d10dab8b9", "26d45aba-f115-4db7-9094-fc20f6a09e32", "d70d4aa8-77d5-44ae-8b32-4176bb4ec66f",
            "3179586c-e2ad-4895-8a74-e045dfc98ee4", "1e9b8b55-619c-4dc2-bf1a-97ca62ca277a", "215ac038-4ef8-4c20-b2de-df90f09d98c9", "a9d1501c-a51b-426d-94a4-ac5862aac309",
            "c5a22be4-0aae-4ec4-931a-c8030c734178", "d05d70aa-1fab-4856-8bc3-f7649e9acd53", "fb168af5-798b-45d8-990f-6fb9eddbb612", "4e02bacc-fb06-4d8a-88f2-3b05d27b53ac",
            "1b4741ac-0097-46d4-b351-9741422208ea", "700b0958-ff49-49d5-abf8-f0915f84d709", "5c76ee6b-9f89-4329-ae5a-3a54b534578f", "82e83816-4dda-4713-8566-2e0ad7f3d967",
            "20f3bd22-0075-40fc-96cb-618cd151e3d0", "2a1db00b-b9af-471f-a601-92e1ba143970", "1807471e-2a3f-4b41-9b5f-81eb2b6b5865", "1fb0809a-f3b5-4f7b-a3f7-f77c58357a48",
            "55e6a013-5159-4a15-85f7-7c8ff69c3564", "aecd55ec-5d2a-4691-99d1-e582981487ab", "2902e0e7-8ded-44d4-8cac-0d53045c1181", "43488f10-f093-41d5-a229-dec31d429716",
            "58aa4e9a-75f0-42be-adce-91a9909932b1", "85c32ad7-0312-4c03-b61d-92aa5c821f1f", "a652420d-b048-4e01-8f9a-b3147765e569", "84871711-a0f0-466a-9a3d-5bf64b4e9038",
            "9650f262-4ceb-415c-b0e0-3dca0f42f782", "7e4a8030-7b8f-4d88-9080-9087d38ae4ce", "dace4757-9745-4a29-9c84-90962a0c2627", "fdc81a83-ad61-4b96-970b-bccf60d6ef5e",
            "43ccd2cd-6f7a-439b-b9f9-b639389321d8", "08e25cb3-0e18-469a-a253-a9e4ef95aaa5", "7096d87d-0c5e-4dcb-97ca-15b8b06f669f", "8054b71a-6b84-44d8-be26-1b8aadeb3ac8",
            "53ab13bc-438d-4d63-a8d8-1bc932aa5e42", "ede09c15-4e96-434a-9ed2-ed5969785ff6", "cf6fd78e-0551-410e-a088-a8e9f6ed87a6", "79a3e217-e809-4cda-a9aa-4d79f72ace38",
            "05b7b0b7-2eff-4fe7-811a-2c7e60fca0ad", "8eedf98f-17d8-4749-bb93-058a245112b2", "e4dafc20-a614-4655-9dc9-43e120269102", "98890f26-4570-43f7-8c0b-40e37b356305",
            "40d8da07-c054-4713-b469-7edc34f9ad01", "1ef785e1-b063-49e4-9528-19025e5ec3d5", "1b234963-4eba-4ec6-8d30-d42c9679c82e", "4acf5d39-0346-4d01-9f95-a40b0f6b3ee9",
            "aa3ba198-532e-445e-817d-455c73705e86", "5adcb9c9-6ef9-48d9-9cdc-e13761c7202c", "ddf547c4-0c2b-4cc7-88d2-398f68d5f561", "5f987b89-f263-4a21-ac86-981b9f2f082f",
            "18848392-d94a-40ff-8a84-e15d82cd42b7", "2f93e3d9-d3af-4f49-875c-35c849710505", "5db22b1f-af9d-493b-83c7-ec66ff3a3eeb", "c01a4ecc-0d60-4453-a95b-7e71b9202e04",
            "0dcd35e6-a80f-4fd0-aebc-4b44ad3ed6fc", "4806d760-2352-4975-829c-b48b2788efcd", "2ef0adf0-f77a-448c-acd7-90476c5acf08", "d3b72dd9-ad93-437b-b406-b1726fb24ffd",
            "d03ccd15-f9de-4e3e-99ed-7383669709f0", "809699e6-5513-4af1-877c-307ee91b7dda", "77dcf093-c3f7-49e9-bf41-f6f3f69cf904", "9de22d33-4eaa-43ea-a0ad-5275dc45d1ac",
            "0e71a236-0e2d-4d48-8cd9-1cc019954344", "8be0a18d-bb1e-4810-96fb-0fe319d453c1", "32a8a15d-d97d-4180-a64b-fd4fd5b10078", "ecb87a7f-7cc1-40a1-9cb3-6d723903d080",
            "6f0fb946-4606-45c6-b960-2cb91c0fb06d", "e872448b-27b5-4936-9bf2-2cc03a178184", "0421ad40-0881-47d4-b086-38c90c1ab62f", "f131c4ad-bf02-4832-b52e-ada9213350a0",
            "f631009f-c997-44b4-949c-cbdb56bac5b7", "74d6ff39-fbc2-4a1a-9223-f3fab44e2603", "7d6921f7-b34a-4e6d-aeaf-9f4cfbc2b7a1", "574c8e7b-af34-4400-bbdb-e25e53ab44c0",
            "c6d949c2-0aec-4ef3-9f0e-37fb7ea75f4e", "9672f2f1-5f2d-4ab9-ad23-186ecc646c7c", "d7c3b716-379f-4896-aac4-ae86d60a3c65", "9b1c2db8-4af3-418f-bc81-78df91f01d7d",
            "b5228d57-0cec-4c41-8938-656de4426ca6", "ff150b1b-247c-41ad-b704-7418e9eeff44", "477af77f-938b-41e2-b8a6-14bd62232a37", "603945bd-7025-4bd8-b6b3-a9c3a4b26dcc",
            "23f904b6-bc48-48bf-b9a1-d1d7f31b4fad", "85ce0811-4a88-4fc7-b524-8637c23291af", "e76c418b-4bf6-4206-aeeb-6dffada1a1ca", "d99f4d54-7301-4952-85a5-f2844ad608d6",
            "b7e4d7b2-7ed4-4cf6-89e9-3abe5a8a73cf", "638865fe-83ce-4026-acd5-e0df70d021d6", "ead02b68-58e5-4895-8a13-d3b133fdc81d", "f33b0d7f-f7f0-4d82-ac8c-7dd7a3102a9d",
            "be16bf44-e559-4077-ad2e-0dd6531f28ea", "b4921d36-14bc-4cac-86d1-3d8f74f8cef4", "94d65057-0da6-4a79-aaca-963265448858", "194ba8d3-14fa-48cc-a785-f9f261952415",
            "0176f58a-3aaa-4fe5-856c-98155bd18dc9", "d3b9e94c-3f04-4fe6-99dd-707792b2c167", "0e57e79b-d3df-440f-aca9-4c46bba4a3bb", "aac7171c-9bac-42a8-b094-2363599dae59",
            "d3b72dd9-ad93-437b-b406-b1726fb24ffd", "9382175a-3243-4278-a181-2f3f68a0a289", "02d1e6b4-5157-4ff3-8382-7862ab0634c4", "4c224e5c-9eac-41a8-9a27-05bd05d1d1a1",
            "0cfef9df-5834-44fc-98c6-afff447ea0e6", "c286c855-c3b7-476c-83a6-8fdf11a4fdcb", "37b8527a-8e9b-4b23-9d3b-6196c9d70551", "faa68807-3fef-45cf-b960-7cdaecb0503c",
            "60f5eafc-56ad-4881-b2d5-bbbdcb41cffd", "dd4207b6-231c-4c9e-9543-852f7941631f", "d33f9e30-9df5-4950-ae6e-a93a031f322f", "ab49cc7b-53e9-424e-8fa1-778186ffae33"
    };

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(skin -> {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerModel = event.getSkin(skin);
            if (playerModel != null) {
                playerModel.addLayer(new DnDesireCosmeticLayer(playerModel, event.getEntityModels(), DnDesireCosmetic));
            }
        });
    }
}
