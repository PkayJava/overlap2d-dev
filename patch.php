<?php

require_once('vqmod/vqmod.php');
require_once('utilities.php');
VQMod::bootup();

recurse_copy(getcwd().'/git/overlap2d', getcwd(). '/source/overlap2d');
recurse_copy(getcwd().'/git/overlap2d-runtime-libgdx', getcwd(). '/source/overlap2d-runtime-libgdx');
recurse_copy(getcwd().'/git/VisEditor', getcwd(). '/source/VisEditor');
recurse_copy(getcwd().'/git/spine-runtimes', getcwd(). '/source/spine-runtimes');
recurse_copy(getcwd().'/git/ashley', getcwd(). '/source/ashley');
// recurse_copy(getcwd().'/', getcwd(). '/source/overlap2d/art/textures');
recurse_copy(getcwd().'/git/overlap2d/art', getcwd(). '/source/overlap2d/assets/art');
recurse_copy(getcwd().'/git/overlap2d/splash', getcwd(). '/source/overlap2d/assets/splash');
recurse_copy(getcwd().'/git/overlap2d/style', getcwd(). '/source/overlap2d/assets/style');

copy(getcwd().'/git/VisEditor/UI/assets-raw/icon-folder-parent.png', getcwd().'/source/overlap2d/assets/art/textures/icon-folder-parent.png');

copy(getcwd().'/src/main/resources/style/uiskin.atlas', getcwd().'/source/overlap2d/assets/style/uiskin.atlas');
copy(getcwd().'/src/main/resources/style/uiskin.png', getcwd().'/source/overlap2d/assets/style/uiskin.png');

patch('83c8b5e1c74e6a2fe429c4216955b3ba259e83d2','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TransformComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TransformComponent.java');
patch('92ee7e6edd6d66f01aeb054320fe05cd5b2803c1','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NodeComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NodeComponent.java');
patch('48419a2bb5f64a78d81fc3577ac8c7da917a4c16','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ParentNodeComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ParentNodeComponent.java');
patch('36c88654cb3cfb5ac1d51d4efb95f5998daffe38','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/PolygonComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/PolygonComponent.java');
patch('9c02552700f8dfad54bd2dd800a6d8b55bebf1ab','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TextureRegionComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TextureRegionComponent.java');
patch('56fbc4a922d6f268af45eed8fff2ccc3cd387169','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physics/PhysicsBodyPropertiesComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physics/PhysicsBodyPropertiesComponent.java');
patch('5cdedd39b981d5c1e821657ec30369ae3bc6881a','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physics/PhysicsBodyComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physics/PhysicsBodyComponent.java');
patch('e75767495bced533dd567e0707b038daf17876b7','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/MainItemComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/MainItemComponent.java');
patch('dc5eba20a1fa4bf1d4b07694405a561c3ff03aa6','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TintComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TintComponent.java');
patch('b58f39adf4dd57dbd8d9ad0b4edb47991ce42301','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ViewPortComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ViewPortComponent.java');
patch('9bc3a217ac285b45c8cb9f88315666ea6202ce51','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/CompositeTransformComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/CompositeTransformComponent.java');
patch('3b1aef744b18d5c6a19f499ea265af2750ecc86b','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/label/LabelComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/label/LabelComponent.java');
patch('d33fe12cd96c48ba5b924d0309e0254dea6d3bbc','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/light/LightObjectComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/light/LightObjectComponent.java');
patch('17074a9691b1bc06935c3105942f8d30642b9293','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NinePatchComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NinePatchComponent.java');
patch('8b5fa78f0c0108d1eaf7fb383f923c9dcac82d04','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterDrawerComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterDrawerComponent.java');
patch('0eb3936cf97b67385e61ca227ff879ab4690f3a8','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/AnimationComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/AnimationComponent.java');
patch('9a78a52d2911601e3515fd7ded491b8db78c8670','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScissorComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScissorComponent.java');
patch('34d68c4ba9ba4348af43835ef871b15975f4d5e9','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterComponent.java');
patch('2c404a4c7ff898d5c03a20d62e976ffb8bff3443','git/overlap2d/src/com/uwsoft/editor/view/stage/input/InputListenerComponent.java','source/overlap2d/src/com/uwsoft/editor/view/stage/input/InputListenerComponent.java');
patch('debf1b4a899a0d431a14f39bad69ebce0614834d','git/overlap2d/src/com/uwsoft/editor/Overlap2D.java','source/overlap2d/src/com/uwsoft/editor/Overlap2D.java');
patch('7617bbeeb880b3828b74751647857e5919dd247e','git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java','source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java');
patch('01bf8053731f17d53aab3dba58d0bce20d92ac6d','git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java','source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java');
patch('e70fe99e3468b55ef95272a86bc9a88500106255','git/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java','source/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java');
patch('146842d7908a0e60ce32cc54505dc1a87859429b','git/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java','source/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java');
patch('78d8cf8bf6205043174f834ef85f8939b7c29415','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ZIndexComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ZIndexComponent.java');
patch('fbf1b49ac6ec0077ac0e5ae114e0f62b3b3f4185','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/LayerMapComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/LayerMapComponent.java');
patch('9e495ac3b1725f9c8795657efbdb6587b98a8512','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java');
patch('add1e3110e7338f01dbbbab4776f7849f8a277eb','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationStateComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationStateComponent.java');
patch('1fcc66139ab8742fc10c50a2b6d6044b94a35a29','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScriptComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScriptComponent.java');
patch('5332f117601dafa03f81275bfec905bf6e3efb77','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/SpineDataComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/SpineDataComponent.java');
patch('fabb8614baa97558e8c1cc14db91a972f8caf90b','git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineObjectComponent.java','source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineObjectComponent.java');
patch('cf51b58c1ea74a8e28f441f0ec13943e065e6c40','git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java','source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java');
patch('3e81b61e76b2209535f42386249c80c857dcb344','git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineDrawableLogic.java','source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineDrawableLogic.java');
patch('ccbcf33f384d1d25ae1f6c88d1a57d1aa55c24b2','git/overlap2d/src/com/uwsoft/editor/splash/SplashStarter.java','source/overlap2d/src/com/uwsoft/editor/splash/SplashStarter.java');
patch('e4af98dac14127f7febb5d3dd3163397f1944832','git/overlap2d/src/com/uwsoft/editor/Main.java','source/overlap2d/src/com/uwsoft/editor/Main.java');
patch('387cb7fb7414e774a8904cc4d3dd47efed868d27','git/overlap2d/assets/style/uiskin.json','source/overlap2d/assets/style/uiskin.json');

patch('67e64c2c9cfc8058d2bc9a3b638b7683c4694ec1','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/DimensionsComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/DimensionsComponent.java');
patch('db248bbf472fccd9cc869fb372e0d7c186d5869a','git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/particle/ParticleComponent.java','source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/particle/ParticleComponent.java');

/**


patch('','git/overlap2d/src/','source/overlap2d/src/');
patch('','git/overlap2d-runtime-libgdx/src/','source/overlap2d-runtime-libgdx/src/');
patch('','git/overlap2d-runtime-libgdx/extensions/spine/src/','source/overlap2d-runtime-libgdx/extensions/spine/src/');
*/