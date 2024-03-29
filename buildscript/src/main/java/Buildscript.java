import io.github.coolcrabs.brachyura.dependency.JavaJarDependency;
import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.SimpleFabricProject;
import io.github.coolcrabs.brachyura.fabric.Yarn;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import net.fabricmc.mappingio.tree.MappingTree;

public class Buildscript extends SimpleFabricProject {

    @Override
    public VersionMeta createMcVersion() {
        return Minecraft.getVersion("1.19.4");
    }

    @Override
    public int getJavaVersion() {
        return 17;
    }

    @Override
    public MappingTree createMappings() {
        return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn("1.19.4+build.2")).tree;
    }

    @Override
    public FabricLoader getLoader() {
        return new FabricLoader(FabricMaven.URL, FabricMaven.loader("0.15.6"));
    }


    @Override
    public void getModDependencies(ModDependencyCollector d) {
        // Libraries
        String[][] fapiModules = new String[][] {
            {"fabric-registry-sync-v0", "2.3.2+95ae8716f4"},
            {"fabric-resource-loader-v0", "0.11.7+8400c67ef4"},
            {"fabric-renderer-api-v1", "2.4.2+90110d8df4"},
            {"fabric-item-group-api-v1", "3.0.10+8400c67ef4"},
            {"fabric-object-builder-api-v1", "7.1.1+d63b52eaf4"},
            {"fabric-rendering-v1", "2.1.5+10ce000ff4"},
            {"fabric-networking-api-v1", "1.3.6+ae9c4c6af4"},
            {"fabric-api-base", "0.4.28+737a6ee8f4"},
            {"fabric-models-v0", "0.4.1+a0255436f4"},
            {"fabric-renderer-indigo", "1.4.2+90110d8df4"},
            {"fabric-entity-events-v1", "1.5.17+10ce000ff4"},
            {"fabric-events-interaction-v0", "0.6.1+4b6b93f0f4"},
            {"fabric-rendering-data-attachment-v1", "0.3.32+10ce000ff4"},
            {"fabric-lifecycle-events-v1", "2.2.19+10ce000ff4"},
            {"fabric-mining-level-api-v1", "2.1.44+10ce000ff4"}
        };
        for (String[] module : fapiModules) {
            d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", module[0], module[1]), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
        }
        // Get fork at https://github.com/WerySkok/Stacc, checkout the bonelessandformatted branch and build it with
        // java -jar ./brachyura-bootstrap-0.jar publishToMavenLocal
        jij(d.addMaven(Maven.MAVEN_LOCAL, new MavenId("net.devtech:stacc:1.5.2+bonelessandformatted"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE));
        // Compat
        d.addMaven("https://maven.shedaniel.me/", new MavenId("me.shedaniel:RoughlyEnoughItems-api-fabric:9.0.475"), ModDependencyFlag.COMPILE);
        d.addMaven("https://maven.vram.io", new MavenId("io.vram:frex-fabric-mc118:6.0.236"), ModDependencyFlag.COMPILE);
        // Compat but for bruh moments
        d.addMaven("https://oskarstrom.net/maven", new MavenId("net.oskarstrom:DashLoader:2.0"), ModDependencyFlag.COMPILE);
        d.add(new JavaJarDependency(Maven.getMavenFileDep("https://gitlab.com/api/v4/projects/21830712/packages/maven", new MavenId("io.github.flemmli97", "flan", "1.18-1.6.6"), "-fabric-api.jar").file, null, null), ModDependencyFlag.COMPILE);
        d.addMaven("https://api.modrinth.com/maven", new MavenId("maven.modrinth", "goml-reserved", "1.5.0-beta.4+1.18.2"), ModDependencyFlag.COMPILE);
    }
}
